package entities;

public class ProducerObserver {
    private Boolean log;
    private final Integer id;
    /**
     * get the id
     */
    public Integer getId() {
        return id;
    }

    public ProducerObserver(Boolean log, int id) {

        this.log = log;
        this.id = id;
    }
    /**
     * get the log
     */
    public Boolean getLog() {
        return log;
    }
    /**
     * set the log
     */
    public void setLog(Boolean log) {
        this.log = log;
    }
    /**
     * update
     */
    public void update() {
            log = true;
    }
}
