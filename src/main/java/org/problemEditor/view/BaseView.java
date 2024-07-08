package org.problemEditor.view;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseView {

    protected ArrayList<JTextComponent> textComponents;

    protected BaseView() {
        this.textComponents = new ArrayList<>();
    }

    public void showValidationError() {
        JOptionPane.showMessageDialog(null, "Please fill in all required fields!", "Warning",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Warning",
                JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    protected void setRedFieldBorderIfEmpty(@NotNull JTextComponent component) {
        if (component.getText().isEmpty()) {
            component.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
    }

    public int showOptionDialog(String title, String message) {
        Object[] options = {"Yes", "No"};
        return JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    public void highlightEmptyFields() {
        for (JTextComponent component : textComponents) {
            if (component.getText().isEmpty()) {
                setRedFieldBorderIfEmpty(component);
            }
        }
    }

    public boolean checkMissingInputs() {
        for (JTextComponent component : textComponents) {
            if (component.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    protected ItemListener toggleLanguageCheckbox(JCheckBox germanCheckBox, JCheckBox englishCheckBox) {
        return e -> {
            if (e.getSource() == germanCheckBox) {
                englishCheckBox.setSelected(!germanCheckBox.isSelected());
            } else if (e.getSource() == englishCheckBox) {
                germanCheckBox.setSelected(!englishCheckBox.isSelected());
            }
        };
    }

    public boolean checkDisjointFields(String left, String right){
        String[] good = left.split(System.lineSeparator());
        String[] bad = right.split(System.lineSeparator());
        Set<String> goodSet = new HashSet<>(Arrays.asList(good));
        Set<String> badSet = new HashSet<>(Arrays.asList(bad));
        return Collections.disjoint(goodSet,badSet);
    }

    public boolean checkWordsAlphabet(String language, String words) {
        Set<Character> uniqueChars = new HashSet<>();

        for (int i = 0; i < language.length(); i++) {
            char currentChar = language.charAt(i);
            if (Character.isLowerCase(currentChar)) {
                uniqueChars.add(currentChar);
            }
        }
        Iterator<Character> iterator = uniqueChars.iterator();
        String alphabet = "E";
        while (iterator.hasNext()) {
                alphabet += iterator.next();
            }

        String plainwords = words.replaceAll("\\s","");
        Pattern pattern = Pattern.compile("(["+alphabet+"]*)");
        Matcher matcher = pattern.matcher(plainwords);
        if (matcher.find()) {
            if (matcher.group(1).equals(plainwords)){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean checkLanguage(String language){
        boolean check = false;
        if (isGrammar(language)) {
            Pattern pattern = Pattern.compile("([a-z01A-Z->\\|\\s,]*)");
            Matcher matcher = pattern.matcher(language);
            if (matcher.find()) {
                if (matcher.group(1).equals(language)) {
                    Pattern patternS = Pattern.compile("S\\s*->");
                    Matcher matcherS = patternS.matcher(language);
                    if (matcherS.find()) {
                        check = true;
                    }
                }
            }
        }else{
            Pattern pattern = Pattern.compile("([a-z01\\+\\*\\|\\s\\(\\)]*)");
            Matcher matcher = pattern.matcher(language);
            if (matcher.find()) {
                if (matcher.group(1).equals(language)) {
                    Pattern patternS = Pattern.compile("[a-z01]+");
                    Matcher matcherS = patternS.matcher(language);
                    if (matcherS.find()) {
                        check = true;
                    }

                }
            }
        }

        return check;
    }

    public boolean isGrammar(String language){
        return language.contains("->");
    }

    public abstract void init();

    protected abstract ArrayList<JTextComponent> createTextComponentsList();

    public abstract JPanel getMainPanel();
}
