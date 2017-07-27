package code.ui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import code.model.Model;

import javax.swing.JButton;

public class EventHandler implements ActionListener {
    private Model _model;

    public EventHandler(Model m) {
        _model = m;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Model.click_counter++;

        _model.border(e.getSource());
        _model.previous_button.add((JButton) e.getSource());

        if (Model.click_counter % 2 == 0) {
            _model.logic(_model.previous_button);
            _model.previous_button.clear(); // only need past and current button, so delete all buttons in this list
                                            // after running logic for two consecutive buttons.
        }
    }

}
