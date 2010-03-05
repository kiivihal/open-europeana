import sys

from django.core.management.base import BaseCommand, CommandError
from optparse import make_option


class Command(BaseCommand):
    option_list = BaseCommand.option_list + (
        #make_option('--daemon', action='store_true', dest='daemon_mode', default=False,
        #    help='Forks and runs in background.'),
        make_option('--forceremove', action='store_true', dest='force_remove', default=False,
            help='Removes pointer to running process.'),
    )
    help = "Handles requests and maintains the cache."
    args = ''#[--daemon]'

    def handle(self, *args, **options):
        from utils import glob_consts
        from apps.cache_machine.models import ProcessMonitoring
        import cmd.sune

        if args:
            raise CommandError('Usage is cachemachine %s' % self.args)

        already_running = ProcessMonitoring.objects.filter(role=glob_consts.PMR_REQ_HANDLER)

        if 1:#options['force_remove']:
            #if not len(already_running):
            #    raise CommandError('Attempt to force remove, but no process is logged')
            print 'Removing pointer to running process.'
            print '  Make sure you dont have one running!!'
            already_running.delete()
            print '  Succeeded to remove process from database.'
            #sys.exit()

        if len(already_running):
            pm = already_running[0]
            raise CommandError('Already running as pid: %i' % pm.pid)
        p = ProcessMonitoring(role=glob_consts.PMR_REQ_HANDLER,
                              sstate=glob_consts.ST_INITIALIZING,
                              pid=-1 # means not yet specified
                              )
        p.save()
        cmd.sune.cachemachine_starter(p)




