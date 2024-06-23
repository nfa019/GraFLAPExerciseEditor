package org.problemEditor.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.problemEditor.model.GrammarModel;
import org.jetbrains.annotations.NotNull;
import org.problemEditor.controller.GrammarController;
import org.problemEditor.enums.GrammarType;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class GrammarView extends BaseView {
    private JTextField languageTextField;
    private JTextField titleTextField;
    private JTextArea generatedWordsTextArea;
    private JTextArea nonGeneratedWordsTextArea;
    private JTextArea descriptionTextArea;
    private JTextArea sampleSolutionTextArea;
    private JCheckBox randomizeLowerCaseCheckBox;
    private JCheckBox englishCheckBox;
    private JCheckBox germanCheckBox;
    private JComboBox<GrammarType> typeComboBox;
    private JButton backButton;
    private JButton createButton;
    private JButton saveDraftButton;
    private JButton previewButton;
    private JButton cancelButton;
    private JPanel OptionalPanel;
    private JPanel MainPanel;
    private GrammarController grammarViewController;

    public GrammarView() {
        super();
    }

    @Override
    public void init() {
        MainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        OptionalPanel.setBorder(new CompoundBorder(new LineBorder(new Color(51, 144, 218), 2),
                new EmptyBorder(5, 5, 5, 5)));
        super.textComponents = this.createTextComponentsList();
        initActionListeners();
    }

    @Override
    protected ArrayList<JTextComponent> createTextComponentsList() {
        ArrayList<JTextComponent> textComponentsList = new ArrayList<>();
        textComponentsList.add(titleTextField);
        textComponentsList.add(descriptionTextArea);
        textComponentsList.add(languageTextField);
        return textComponentsList;
    }

    private void initActionListeners() {
        typeComboBox.setModel(new DefaultComboBoxModel<>(GrammarType.values()));

        ItemListener germanOrEnglishCheckboxListener = toggleLanguageCheckbox(germanCheckBox, englishCheckBox);

        germanCheckBox.addItemListener(germanOrEnglishCheckboxListener);
        englishCheckBox.addItemListener(germanOrEnglishCheckboxListener);

        backButton.addActionListener(e -> grammarViewController.handleBackButton());
        cancelButton.addActionListener(e -> grammarViewController.handleCancelButton());
        saveDraftButton.addActionListener(e -> grammarViewController.handleSaveDraftButton());
        previewButton.addActionListener(e -> grammarViewController.handlePreviewButton());
        createButton.addActionListener(e -> grammarViewController.handleCreateButton());
    }

    public void update(GrammarModel model) {
        updateCheckBoxes(model);
        updateTextComponents(model);
        typeComboBox.setSelectedItem(model.getType());
    }

    private void updateCheckBoxes(@NotNull GrammarModel model) {
        if (Objects.equals(model.getChosenLanguage(), Locale.GERMAN)) {
            germanCheckBox.setSelected(true);
        } else if (Objects.equals(model.getChosenLanguage(), Locale.ENGLISH)) {
            englishCheckBox.setSelected(true);
        }
        randomizeLowerCaseCheckBox.setSelected(model.isRandomizeLowerCase());
    }

    private void updateTextComponents(@NotNull GrammarModel model) {
        titleTextField.setText(model.getTitle());
        descriptionTextArea.setText(model.getDescription());
        languageTextField.setText(model.getLanguage());
        generatedWordsTextArea.setText(model.getGeneratedWords());
        nonGeneratedWordsTextArea.setText(model.getNonGeneratedWords());
    }

    public void setGrammarViewController(GrammarController grammarViewController) {
        this.grammarViewController = grammarViewController;
    }

    public String getLanguageText() {
        return languageTextField.getText();
    }

    public boolean isRandomizeLowerCaseIsSelected() {
        return randomizeLowerCaseCheckBox.isSelected();
    }

    public GrammarType getSelectedGrammarType() {
        return (GrammarType) typeComboBox.getSelectedItem();
    }

    public String getGeneratedWordsText() {
        return generatedWordsTextArea.getText();
    }

    public String getNonGeneratedWordsText() {
        return nonGeneratedWordsTextArea.getText();
    }

    public String getTitleText() {
        return titleTextField.getText();
    }

    public String getDescriptionText() {
        return descriptionTextArea.getText();
    }

    public String getSampleSolutionText() {
        return sampleSolutionTextArea.getText();
    }

    public boolean isGermanSelected() {
        return germanCheckBox.isSelected();
    }

    @Override
    public JPanel getMainPanel() {
        return MainPanel;
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
        MainPanel.setLayout(new GridLayoutManager(13, 4, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 18, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Grammar");
        MainPanel.add(label1, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 7, new Insets(0, 0, 0, 0), -1, -1));
        MainPanel.add(panel1, new GridConstraints(12, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
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
        panel2.setLayout(new GridLayoutManager(11, 2, new Insets(0, 0, 0, 0), -1, -1));
        MainPanel.add(panel2, new GridConstraints(1, 2, 11, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(440, -1), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(8, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(0, 0, 8, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(440, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Please enter the generated and non-generated words here");
        panel3.add(label2, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("If nothing is entered, it will be generated automatically");
        panel3.add(label3, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("generated");
        panel3.add(label4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("non-generated");
        panel3.add(label5, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(3, 0, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(215, 150), null, 0, false));
        generatedWordsTextArea = new JTextArea();
        generatedWordsTextArea.setColumns(0);
        generatedWordsTextArea.setRows(0);
        scrollPane1.setViewportView(generatedWordsTextArea);
        final JScrollPane scrollPane2 = new JScrollPane();
        panel3.add(scrollPane2, new GridConstraints(3, 1, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(215, -1), null, 0, false));
        nonGeneratedWordsTextArea = new JTextArea();
        scrollPane2.setViewportView(nonGeneratedWordsTextArea);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(5, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel4, new GridConstraints(8, 0, 3, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Please enter the title of the task here");
        panel4.add(label6, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleTextField = new JTextField();
        titleTextField.setText("");
        panel4.add(titleTextField, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Please enter the task description here");
        panel4.add(label7, new GridConstraints(3, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel4.add(scrollPane3, new GridConstraints(4, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 100), null, 0, false));
        descriptionTextArea = new JTextArea();
        descriptionTextArea.setColumns(0);
        descriptionTextArea.setRows(0);
        scrollPane3.setViewportView(descriptionTextArea);
        final JLabel label8 = new JLabel();
        label8.setText("Please select the language of the task");
        panel4.add(label8, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        englishCheckBox = new JCheckBox();
        englishCheckBox.setText("English");
        panel4.add(englishCheckBox, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        germanCheckBox = new JCheckBox();
        germanCheckBox.setText("German");
        panel4.add(germanCheckBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(11, 2, new Insets(0, 0, 0, 0), -1, -1));
        MainPanel.add(panel5, new GridConstraints(1, 0, 11, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(440, -1), null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(5, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel5.add(panel6, new GridConstraints(0, 0, 8, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Language given by");
        panel6.add(label9, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("In a form like: S −> aB , B −> bS | E | a , S −> aSa");
        panel6.add(label10, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        languageTextField = new JTextField();
        panel6.add(languageTextField, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        randomizeLowerCaseCheckBox = new JCheckBox();
        randomizeLowerCaseCheckBox.setText("Should the letters be selected randomly?");
        panel6.add(randomizeLowerCaseCheckBox, new GridConstraints(3, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        OptionalPanel = new JPanel();
        OptionalPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(OptionalPanel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(300, -1), null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Optional");
        OptionalPanel.add(label11, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Type");
        OptionalPanel.add(label12, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        typeComboBox = new JComboBox();
        OptionalPanel.add(typeComboBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel6.add(spacer3, new GridConstraints(4, 1, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JScrollPane scrollPane4 = new JScrollPane();
        panel5.add(scrollPane4, new GridConstraints(9, 0, 2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        sampleSolutionTextArea = new JTextArea();
        scrollPane4.setViewportView(sampleSolutionTextArea);
        final JLabel label13 = new JLabel();
        label13.setText("Please enter some text as a sample solution");
        panel5.add(label13, new GridConstraints(8, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MainPanel;
    }

}
