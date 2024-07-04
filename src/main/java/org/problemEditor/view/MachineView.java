package org.problemEditor.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.jetbrains.annotations.NotNull;
import org.problemEditor.enums.Determinism;
import org.problemEditor.enums.MachineType;
import org.problemEditor.controller.MachineController;
import org.problemEditor.model.MachineModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MachineView extends BaseView {
    private JTextField titleTextField;
    private JTextField chosenFileTextField;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JTextArea descriptionTextArea;
    private JTextArea sampleSolutionTextArea;
    private JCheckBox englishCheckBox;
    private JCheckBox germanCheckBox;
    private JComboBox<MachineType> typeComboBox;
    private JComboBox<Determinism> determinismComboBox;
    private JButton chooseFileButton;
    private JButton backButton;
    private JButton createButton;
    private JButton saveDraftButton;
    private JButton previewButton;
    private JButton cancelButton;
    private JPanel MainPanel;
    private MachineController machineViewController;

    public MachineView() {
        super();
    }

    @Override
    public void init() {
        MainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        super.textComponents = this.createTextComponentsList();
        initActionListeners();
        initMouseListener();
        initComboBoxes();
    }

    private void initActionListeners() {
        ItemListener germanOrEnglishCheckboxListener = toggleLanguageCheckbox(germanCheckBox, englishCheckBox);

        germanCheckBox.addItemListener(germanOrEnglishCheckboxListener);
        englishCheckBox.addItemListener(germanOrEnglishCheckboxListener);

        chooseFileButton.addActionListener(e -> {
            machineViewController.handleChooseFileButton();
            chosenFileTextField.setBorder(UIManager.getBorder("TextField.border"));
        });

        backButton.addActionListener(e -> machineViewController.handleBackButton());
        cancelButton.addActionListener(e -> machineViewController.handleCancelButton());
        saveDraftButton.addActionListener(e -> machineViewController.handleSaveDraftButton());
        previewButton.addActionListener(e -> machineViewController.handlePreviewButton());
        createButton.addActionListener(e -> machineViewController.handleCreateButton());
    }

    private void initComboBoxes() {
        typeComboBox.setModel(new DefaultComboBoxModel<>(MachineType.values()));
        determinismComboBox.setModel(new DefaultComboBoxModel<>(Determinism.values()));
    }

    @Override
    protected ArrayList<JTextComponent> createTextComponentsList() {
        ArrayList<JTextComponent> textComponentsList = new ArrayList<>();
        textComponentsList.add(inputTextArea);
        textComponentsList.add(outputTextArea);
        textComponentsList.add(titleTextField);
        textComponentsList.add(descriptionTextArea);
        textComponentsList.add(chosenFileTextField);
        return textComponentsList;
    }

    private void initMouseListener() {
        for (JTextComponent component : textComponents) {
            if (!component.equals(chosenFileTextField)) {
                component.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        component.setBorder(UIManager.getBorder("TextField.border"));
                    }
                });
            }
        }
    }

    public void setMachineViewController(MachineController machineViewController) {
        this.machineViewController = machineViewController;
    }

    public void setChosenFileTextField(String absolutePath) {
        this.chosenFileTextField.setText(absolutePath);
    }

    public void update(MachineModel model) {
        updateCheckBoxes(model);
        updateComboBoxes(model);
        updateTextComponents(model);
    }

    private void updateCheckBoxes(@NotNull MachineModel model) {
        if (Objects.equals(model.getChosenLanguage(), Locale.GERMAN)) {
            germanCheckBox.setSelected(true);
        } else if (Objects.equals(model.getChosenLanguage(), Locale.ENGLISH)) {
            englishCheckBox.setSelected(true);
        }
    }

    private void updateComboBoxes(@NotNull MachineModel model) {
        typeComboBox.setSelectedItem(model.getType());
        determinismComboBox.setSelectedItem(model.getDeterminism());
    }

    private void updateTextComponents(@NotNull MachineModel model) {
        titleTextField.setText(model.getTitle());
        descriptionTextArea.setText(model.getDescription());
        sampleSolutionTextArea.setText(model.getSampleSolution());
        chosenFileTextField.setText(model.getJffPathName());
        inputTextArea.setText(model.getInput());
        outputTextArea.setText(model.getOutput());
    }

    @Override
    public JPanel getMainPanel() {
        return MainPanel;
    }

    public MachineType getSelectedMachineType() {
        return (MachineType) typeComboBox.getSelectedItem();
    }

    public Determinism getSelectedDeterminism() {
        return (Determinism) determinismComboBox.getSelectedItem();
    }

    public String getInputText() {
        return inputTextArea.getText();
    }

    public String getOutputText() {
        return outputTextArea.getText();
    }

    public String getTitleText() {
        return titleTextField.getText();
    }

    public String getDescriptionText() {
        return descriptionTextArea.getText();
    }

    public String getChosenFileText() {
        return chosenFileTextField.getText();
    }

    public String getSampleSolutionText() {
        return sampleSolutionTextArea.getText();
    }

    public boolean isGermanSelected() {
        return germanCheckBox.isSelected();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        MainPanel = new JPanel();
        MainPanel.setLayout(new GridLayoutManager(15, 4, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Machine");
        MainPanel.add(label1, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 7, new Insets(0, 0, 0, 0), -1, -1));
        MainPanel.add(panel1, new GridConstraints(14, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setText("Back");
        panel1.add(backButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        createButton = new JButton();
        createButton.setText("Create");
        panel1.add(createButton, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        saveDraftButton = new JButton();
        saveDraftButton.setText("Save Draft");
        panel1.add(saveDraftButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        previewButton = new JButton();
        previewButton.setText("Preview");
        panel1.add(previewButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        panel1.add(cancelButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(12, 2, new Insets(0, 0, 0, 0), -1, -1));
        MainPanel.add(panel2, new GridConstraints(1, 2, 12, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(8, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(0, 0, 11, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(440, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Please enter the input and output of the machine here");
        panel3.add(label2, new GridConstraints(0, 0, 2, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Input");
        panel3.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Output");
        panel3.add(label4, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(3, 0, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(215, 150), null, 0, false));
        inputTextArea = new JTextArea();
        scrollPane1.setViewportView(inputTextArea);
        final JScrollPane scrollPane2 = new JScrollPane();
        panel3.add(scrollPane2, new GridConstraints(3, 1, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(215, -1), null, 0, false));
        outputTextArea = new JTextArea();
        scrollPane2.setViewportView(outputTextArea);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(5, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel4, new GridConstraints(11, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Please enter the title of the task here");
        panel4.add(label5, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleTextField = new JTextField();
        titleTextField.setText("");
        panel4.add(titleTextField, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Please enter the task description here");
        panel4.add(label6, new GridConstraints(3, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel4.add(scrollPane3, new GridConstraints(4, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 100), null, 0, false));
        descriptionTextArea = new JTextArea();
        descriptionTextArea.setColumns(0);
        descriptionTextArea.setRows(0);
        scrollPane3.setViewportView(descriptionTextArea);
        final JLabel label7 = new JLabel();
        label7.setText("Please select the language of the task");
        panel4.add(label7, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        englishCheckBox = new JCheckBox();
        englishCheckBox.setText("English");
        panel4.add(englishCheckBox, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        germanCheckBox = new JCheckBox();
        germanCheckBox.setText("German");
        panel4.add(germanCheckBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(12, 2, new Insets(0, 0, 0, 0), -1, -1));
        MainPanel.add(panel5, new GridConstraints(1, 0, 12, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(7, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel6, new GridConstraints(0, 0, 8, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(440, -1), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Please select the machine type here");
        panel6.add(label8, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("If it's a Turing machine, please choose determinism");
        panel6.add(label9, new GridConstraints(1, 0, 2, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Please select the Jff file of the sample solution automaton here");
        panel6.add(label10, new GridConstraints(5, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chosenFileTextField = new JTextField();
        panel6.add(chosenFileTextField, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        chooseFileButton = new JButton();
        chooseFileButton.setText("Choose File");
        panel6.add(chooseFileButton, new GridConstraints(6, 1, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(panel7, new GridConstraints(3, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Type");
        panel7.add(label11, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        typeComboBox = new JComboBox();
        panel7.add(typeComboBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Determinism");
        panel7.add(label12, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        determinismComboBox = new JComboBox();
        panel7.add(determinismComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane4 = new JScrollPane();
        panel5.add(scrollPane4, new GridConstraints(9, 0, 3, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        sampleSolutionTextArea = new JTextArea();
        scrollPane4.setViewportView(sampleSolutionTextArea);
        final JLabel label13 = new JLabel();
        label13.setText("Please enter some text as a sample solution");
        panel5.add(label13, new GridConstraints(8, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MainPanel;
    }

}
