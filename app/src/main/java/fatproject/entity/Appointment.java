package fatproject.entity;

import java.sql.Timestamp;

/**
 * Created by Victor on 15.05.2018.
 */

public class Appointment {
    private Long id;
    private Long employerId;
    private Long employeeId;
    private Long questionId;
    private Timestamp timeFor;
    private boolean acceeptedByEmployer;
    private boolean acceptedByEmployee;
    private boolean ended;
    private boolean successForEmployer;
    private boolean successForEmployee;
    private boolean started;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Timestamp getTimeFor() {
        return timeFor;
    }

    public void setTimeFor(Timestamp timeFor) {
        this.timeFor = timeFor;
    }

    public boolean isAcceeptedByEmployer() {
        return acceeptedByEmployer;
    }

    public void setAcceeptedByEmployer(boolean acceeptedByEmployer) {
        this.acceeptedByEmployer = acceeptedByEmployer;
    }

    public boolean isAcceptedByEmployee() {
        return acceptedByEmployee;
    }

    public void setAcceptedByEmployee(boolean acceptedByEmployee) {
        this.acceptedByEmployee = acceptedByEmployee;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public boolean isSuccessForEmployer() {
        return successForEmployer;
    }

    public void setSuccessForEmployer(boolean successForEmployer) {
        this.successForEmployer = successForEmployer;
    }

    public boolean isSuccessForEmployee() {
        return successForEmployee;
    }

    public void setSuccessForEmployee(boolean successForEmployee) {
        this.successForEmployee = successForEmployee;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
