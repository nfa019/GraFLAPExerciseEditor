package org.problemEditor;

import org.problemEditor.enums.PanelName;
import org.jetbrains.annotations.NotNull;
import org.problemEditor.controller.*;
import org.problemEditor.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class GraFlapProblemEditor {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private StartView startView;
    private ChooseView chooseView;
    private AutomatonView automatonView;
    private GrammarView grammarView;
    private MachineView machineView;
    private Dimension startSize;

    public void startUI() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }
            createAndShowUI();
            new ControllerFactory().createController(this);
        });
    }

    private void createAndShowUI() {
        frame = new JFrame("GraFLAP Problem Editor");
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        createUI();
        initCardPanel();

        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        startSize = chooseView.getMainPanel().getPreferredSize();
        frame.setSize(startSize);
        frame.setVisible(true);
    }

    private void createUI() {
        startView = new StartView();
        chooseView = new ChooseView();
        automatonView = new AutomatonView();
        automatonView.init();
        grammarView = new GrammarView();
        grammarView.init();
        machineView = new MachineView();
        machineView.init();
    }

    private void initCardPanel() {
        cardPanel.add(startView.getMainPanel(), PanelName.START.getName());
        cardPanel.add(chooseView.getMainPanel(), PanelName.CHOOSE.getName());
        cardPanel.add(automatonView.getMainPanel(), PanelName.AUTOMATON.getName());
        cardPanel.add(grammarView.getMainPanel(), PanelName.GRAMMAR.getName());
        cardPanel.add(machineView.getMainPanel(), PanelName.MACHINE.getName());
    }

    public void navigateTo(@NotNull PanelName direction) {
        cardLayout.show(cardPanel, direction.getName());
        if (Objects.equals(direction, PanelName.START) || Objects.equals(direction, PanelName.CHOOSE)) {
            frame.setSize(startSize);
        } else if (Objects.equals(direction, PanelName.AUTOMATON)) {
            frame.setSize(automatonView.getMainPanel().getPreferredSize());
        } else if (Objects.equals(direction, PanelName.GRAMMAR)) {
            frame.setSize(grammarView.getMainPanel().getPreferredSize());
        } else if (Objects.equals(direction, PanelName.MACHINE)) {
            frame.setSize(machineView.getMainPanel().getPreferredSize());
        }
    }

    public AutomatonView getAutomatonView() {
        return automatonView;
    }

    public GrammarView getGrammarView() {
        return grammarView;
    }

    public MachineView getMachineView() {
        return machineView;
    }

    public void setStartViewController(StartController startController) {
        this.startView.setStartViewController(startController);
    }

    public void setChooseViewController(ChooseController chooseController) {
        this.chooseView.setChooseViewController(chooseController);
    }

}
