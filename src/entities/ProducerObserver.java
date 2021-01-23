package entities;

import java.util.Observable;
import java.util.Observer;

public class ProducerObserver implements Observer {
    private Boolean log;

    public ProducerObserver(Boolean log) {
        this.log = log;
    }

    public Boolean getLog() {
        return log;
    }

    public void setLog(Boolean log) {
        this.log = log;
    }

    @Override
    public void update(Observable o, Object arg) {
            log = true;
    }
}
