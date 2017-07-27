package code.model;

//import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.JButton;
import code.ui.UI;

public class Model {

    public static int click_counter = 0;
    public ArrayList<JButton> previous_button;

    private UI _observer; // initialized by setObserver method
    private Random _rand; // for randomization
    private ArrayList<String> _images; // the names of the image files
    private ArrayList<String> _spinnerCurrentValues; // the image files to display in the UI

    public Model() {
        previous_button = new ArrayList<JButton>();
        _rand = new Random();
        _images = new ArrayList<String>();

        _images.add("Tile-0.png");
        _images.add("Tile-1.png");
        _images.add("Tile-2.png");
        _images.add("Tile-3.png");
        _images.add("Tile-4.png");
        _images.add("Tile-5.png");

        _spinnerCurrentValues = new ArrayList<String>();
        for (int i = 0; i < 25; i = i + 1) {
            _spinnerCurrentValues.add(i, null);
        }

        spin(); // randomly select which images to display
    }

    public void spin() {
        for (int i = 0; i < 25; i++) {
            _spinnerCurrentValues.set(i, _images.get(_rand.nextInt(_images.size())));
        }
        stateChanged(); // tell the UI that the model's state has changed
    }

    public void logic(ArrayList<JButton> previous_button) {
        if (previous_button.size() == 2) {
            for (JButton b : previous_button) {
                _observer.remove_border(b); // no need to keep the border after second button is selected.
            }

            // if the two buttons are adjacent, then swap their colors.

            if ((previous_button.get(0).getBounds().y == previous_button.get(1).getBounds().y)
                    || (previous_button.get(0).getBounds().x == previous_button.get(1).getBounds().x)) {

                swap(previous_button.get(0), previous_button.get(1));

                // after every swap, check for the match.
                if (jackpot()) {
                    System.out.println("The board has a match.");
                } else if (!jackpot()) {
                    System.out.println("The board has no match.");
                }

            } else {
                _observer.remove_border(previous_button.get(0));
            }
        }
    }

    public void swap(JButton b1, JButton b2) {
        Icon temp = b2.getIcon();
        b2.setIcon(b1.getIcon());
        b1.setIcon(temp);
    }

    public void border(Object button) {
        _observer.border(button);
    }

    public boolean jackpot() {
        // _observer.update();
        Component[] components = _observer._frame.getContentPane().getComponents();

        for (int i = 0; i < components.length; i++) {
            if (i % 5 < 3) {
                if ((((JButton) components[i]).getIcon().toString()
                        .contentEquals((((JButton) components[i + 1]).getIcon().toString())))
                        && ((JButton) components[i]).getIcon().toString()
                                .contentEquals((((JButton) components[i + 2]).getIcon().toString()))) {
                    return true;
                }
            }
            if (i < 14) {
                if ((((JButton) components[i]).getIcon().toString()
                        .contentEquals((((JButton) components[i + 5]).getIcon().toString())))
                        && ((JButton) components[i]).getIcon().toString()
                                .contentEquals((((JButton) components[i + 10]).getIcon().toString()))) {
                    return true;
                }
            }

            // System.out.println(((JButton) components[i]).getIcon());
        }
        return false;
    }

    public void addObserver(UI ui) {
        _observer = ui;
    }

    public void stateChanged() {
        if (_observer != null) {
            _observer.update(); // tell the UI to update
        }
    }

    public String getImageFileName(int i) {
        return _spinnerCurrentValues.get(i);
    }

}
