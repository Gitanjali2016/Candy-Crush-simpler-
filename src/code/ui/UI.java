package code.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import code.model.Model;

public class UI implements Runnable {

    public JFrame _frame;
    private Model _model;

    public ArrayList<JButton> _buttons;

    @Override
    public void run() {

        _buttons = new ArrayList<JButton>();
        _frame = new JFrame("Gitanjali Nandi's Lab 11");
        _frame.getContentPane().setLayout(new GridLayout(5, 5));

        for (int j = 0; j < 25; j++) {
            JButton button = new JButton();
            _buttons.add(button);
            _frame.getContentPane().add(button);
        }
        _model = new Model();
        _model.addObserver(this);
        organize();
        _frame.pack();
        _frame.setVisible(true);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public JButton border(Object b) {
        JButton button = (JButton) b;
        Border line = new LineBorder(Color.RED, 5);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
        return button;

    }

    public JButton remove_border(Object b) {
        JButton button = (JButton) b;
        Border line = new LineBorder(Color.gray);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
        return button;

    }

    public void organize() {
        while (true) {
            for (int i = 0; i < 25; i++) {
                _buttons.get(i).setIcon(new ImageIcon("Images/" + _model.getImageFileName(i)));
                ActionListener x = new EventHandler(_model);
                _buttons.get(i).addActionListener(x);
            }
            if (!_model.jackpot()) {
                // if (oneMoveSuccess()) {
                break;
                // } else {
                // continue;
                // }

            } else {
                continue;
            }
        }
        _frame.pack();
        _frame.setVisible(true);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public boolean oneMoveSuccess() {
        // TODO: what do we do here? Not sure how to check that there will be success after one move.
        
         for (int i = 0; i < 25; i++) {
         for (int j = 0; j < 25; j++) {
         if (i != j) {
         _model.swap(_buttons.get(i), _buttons.get(j));
         if (_model.jackpot()) {
         return true;
         } else {
         continue;
         }
         }
         }
         }
         
        return false;
    }

    public boolean update() {
        for (int i = 0; i < 25; i++) {
            _buttons.get(i).setIcon(new ImageIcon("Images/" + _model.getImageFileName(i)));
            ActionListener x = new EventHandler(_model);
            _buttons.get(i).addActionListener(x);
        }
        _frame.pack();
        return _model.jackpot();
    }
}
