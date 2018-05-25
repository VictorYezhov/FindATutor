package fatproject.SendingForms;

import java.sql.Timestamp;
import java.util.Calendar;

public class IdsForAppointment {
    private Long id_employ;
    private Long id_employer;
    private Long question_id;


    public IdsForAppointment(Long id_employ, Long id_employer, Long question_id) {
        this.id_employ = id_employ;
        this.id_employer = id_employer;
        this.question_id = question_id;
    }

    public Long getId_employer() {
        return id_employer;
    }

    public void setId_employer(Long id_employer) {
        this.id_employer = id_employer;
    }

    public Long getId_employ() {
        return id_employ;
    }

    public void setId_employ(Long id_employ) {
        this.id_employ = id_employ;
    }

    public Long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }
}
