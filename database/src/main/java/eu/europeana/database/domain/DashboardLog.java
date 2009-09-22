package eu.europeana.database.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Gerald de Jong, Beautiful Code BV, <geralddejong@gmail.com>
 */

@Entity
public class DashboardLog implements Serializable {
    private static final long serialVersionUID = -2184440316137953279L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private Long id;

    @Column(nullable = false, length = 60)
    private String who;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Index(name = "dashboardlogwhenindex")
    private Date time;

    @Lob
    private String what;

    public DashboardLog() {
    }

    public DashboardLog(String who, Date time, String what) {
        this.who = who;
        this.time = time;
        this.what = what;
    }

    public Long getId() {
        return id;
    }

    public String getWho() {
        return who;
    }

    public Date getTime() {
        return time;
    }

    public String getWhat() {
        return what;
    }

    public String toString() {
        return who+":"+ time +":"+what;
    }
}