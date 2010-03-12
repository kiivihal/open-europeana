"""
 Copyright 2010 EDL FOUNDATION

 Licensed under the EUPL, Version 1.1 or as soon they
 will be approved by the European Commission - subsequent
 versions of the EUPL (the "Licence");
 you may not use this work except in compliance with the
 Licence.
 You may obtain a copy of the Licence at:

 http://ec.europa.eu/idabc/eupl

 Unless required by applicable law or agreed to in
 writing, software distributed under the Licence is
 distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 express or implied.
 See the Licence for the specific language governing
 permissions and limitations under the Licence.


 Created by: Jacob Lundqvist (Jacob.Lundqvist@gmail.com)

"""
import os
from exceptions import Exception
import threading

from glob_consts import LCK_ITEM, LCK_CACHESOURCE
from apps.cache_machine.models import CacheSource, CacheItem




"""
This system uses separate subprocesses and threads to do most of the actual
work on managing cache items. In order to avoid collitions we have a global lock
that makes sure that only one process can attempt to change ownership of a task
"""
proc_lock = threading.Lock()



class ProcControlError(Exception):
    """
    Exception class indicating a problem while executing a management
    command.

    If this exception is raised during the execution of a management
    command, it will be caught and turned into a nicely-printed error
    message to the appropriate output stream (i.e., stderr); as a
    result, raising this exception (with a sensible description of the
    error) is the preferred way to indicate that something has gone
    wrong in the execution of a command.

    """
    pass

class ProcessOwnership(object):
    def __init__(self, lock_name, state):
        if lock_name == LCK_ITEM:
            self.m = CacheItem
        elif lock_name == LCK_CACHESOURCE:
            self.m = CacheSource
        else:
            raise ProcControlError('No such lockeable object: %s' % lock_name)

        self.state = state


    def set_ownership(self, pk, pid, abort_on_running=True):
        self._lock_it()
        item = self.m.objects.get(pk=pk)
        if item.pid and abort_on_running:
            self.do_abort_if_running(item)
        item.pid = pid
        item.sstate = self.state
        item.save()
        self.release_it()
        return item

    def clear_dead_procs(self):
        "returns number of items that where cleared."
        r = 0
        for item in self.m.objects.filter(pid__gt=0):
            if self.is_pid_running(item.pid):
                raise ProcControlError('Attempt to clear a living process on %s, item: %s' % (lock_name,item.pk))
            else:
                self.set_ownership(item.pk, 0, abort_on_running=False)
                r += 1
            pass
        return r

    def is_pid_running(self, pid):
        if os.system('ps ax | grep %i | grep -v grep' % pid):
            b = False
        else:
            b = True
        return b


    def do_abort_if_running(self, item):
        self.release_it()
        msg = '%s id: %s owned by' % (str(item), item.pk)
        if self.is_pid_running(item.pid):
            raise ProcControlError(msg + 'running proc %s' % item.pid)
        else:
            locks[lock_name].release()
            raise ProcControlError(msg + 'dead proc %s' % item.pid)
        return True

    def _lock_it(self):
        proc_lock.acquire()

    def release_it(self):
        proc_lock.release()


def set_process_ownership(lock_name,
                          pk,
                          state,
                          pid, # pid of requestor, doesnt have to be a pid
                          # could be any other id, mumeric is asumed however
                          _abort_on_running = True  # internal use only!!!
                          ):
    """
    Required fields for items compatible with this
    pid = owning process if any or 0
    sstate = state item should be put into

    To avoid the race condition where proc a is starting and wants to take care
    of retrieving a image, then before it has locked that image to itself another
    process/thread also want to get to the same, they must tell us what they belive
    is the previous state.

    ------   Time 0 both want access, changing state 10 => 20   -----

    A  [pidof A]   20        Item state: 10
    B  [pidof B]   20        Item PID:   None


    -------   Time 1 A has aquired a lock and now B gets a go   -------

    A already done   Item state: 20
    B [pidof B 20    Item PID:   [pidof A]

    Since B realisez That pidof A is currently "owning" the item it double checks
    if there is a [pidof A], if it is it just fails. If the process has disapeared
    without releasing the lock (flagged by an item pid) a system error is logged
    also
    """

    #if not locks.has_key(lock_name):
    #    raise ProcControlError('Invalid lock reference: %s' % lock)
    po = ProcessOwnership(lock_name, state)
    return po.set_ownership(pk, pid)

def clear_dead_procs(lock_name, reset_state):
    po = ProcessOwnership(lock_name, reset_state)
    return po.clear_dead_procs()

