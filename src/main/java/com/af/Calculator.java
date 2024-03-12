package com.af;

import com.af.util.operations.BinaryOperation;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Calculator {
    private static BinaryOperation currentOperation = null;
    private static Double firstNumber = null;
    private static Double secondNumber = null;

    private static void createGUI() {
        JFrame frame = createManeFrame();
        JLabel resultLabel = createLabel();
        JButton eraseButton = createButton("AC", new Point(0, 50), () -> {
            resultLabel.setText("0");
            currentOperation = null;
            firstNumber = 0d;
            secondNumber = null;

            return true;
        });
        JButton invertButton = createButton("+/-", new Point(56, 50), () -> {
            double number = Double.parseDouble(resultLabel.getText());
            if ((byte) number != 0) {
                number *= -1;
                resultLabel.setText(String.valueOf(number));
            }

            return true;
        });
        JButton percentButton = createButton("%", new Point(112, 50), () -> {
            double number = Double.parseDouble(resultLabel.getText());
            number /= 100;
            resultLabel.setText(String.valueOf(number));

            return true;
        });
        JButton divideButton = createButton("/", new Point(168, 50), () -> {
            currentOperation = BinaryOperation.DIVISION;
            firstNumber = Double.parseDouble(resultLabel.getText());

            return true;
        });

        JButton sevenButton = createButton("7", new Point(0, 105),
                () -> performActionForNumberButton(resultLabel, "7"));
        JButton eightButton = createButton("8", new Point(56, 105),
                () -> performActionForNumberButton(resultLabel, "8"));
        JButton nineButton = createButton("9", new Point(112, 105),
                () -> performActionForNumberButton(resultLabel, "9"));
        JButton multiplicationButton = createButton("x", new Point(168, 105), () -> {
            currentOperation = BinaryOperation.MULTIPLICATION;
            firstNumber = Double.parseDouble(resultLabel.getText());

            return true;
        });

        JButton fourButton = createButton("4", new Point(0, 160),
                () -> performActionForNumberButton(resultLabel, "4"));
        JButton fiveButton = createButton("5", new Point(56, 160), () -> {
            performActionForNumberButton(resultLabel, "5");

            return true;
        });
        JButton sixButton = createButton("6", new Point(112, 160),
                () -> performActionForNumberButton(resultLabel, "6"));
        JButton subtractionButton = createButton("-", new Point(168, 160), () -> {
            currentOperation = BinaryOperation.SUBTRACTION;
            firstNumber = Double.parseDouble(resultLabel.getText());

            return true;
        });

        JButton oneButton = createButton("1", new Point(0, 215),
                () -> performActionForNumberButton(resultLabel, "1"));
        JButton twoButton = createButton("2", new Point(56, 215),
                () -> performActionForNumberButton(resultLabel, "2"));
        JButton threeButton = createButton("3", new Point(112, 215),
                () -> performActionForNumberButton(resultLabel, "3"));
        JButton additionButton = createButton("+", new Point(168, 215), () -> {
            currentOperation = BinaryOperation.ADDITION;
            firstNumber = Double.parseDouble(resultLabel.getText());

            return true;
        });

        JButton zeroButton = createButton("0", new Point(0, 270),
                () -> performActionForNumberButton(resultLabel, "0"));
        JButton commaButton = createButton(".", new Point(56, 270), () -> {
            //TODO: add a comma logic
            String number = resultLabel.getText();
            if (!number.contains(".")) {
                number += ".";
            }
            resultLabel.setText(number);

            return true;
        });
        JButton equalsButton = createButton("=", new Point(168, 270), () -> {
            if (currentOperation != null) {
                secondNumber = Double.parseDouble(resultLabel.getText());

                double result = switch (currentOperation) {
                    case DIVISION -> firstNumber / secondNumber;
                    case MULTIPLICATION -> firstNumber * secondNumber;
                    case SUBTRACTION -> firstNumber - secondNumber;
                    case ADDITION -> firstNumber + secondNumber;
                };

                String resultValue = Double.parseDouble(String.format("%.3f", result)) % 2 == 0 ?
                        String.valueOf(result).replaceFirst("\\.\\d", "")
                        : String.valueOf(result);
                resultLabel.setText(resultValue);
            }

            return true;
        });

        Stream<Component> stream = Stream.of(
                resultLabel,
                eraseButton,
                invertButton,
                percentButton,
                divideButton,
                eightButton,
                sevenButton,
                eraseButton,
                nineButton,
                multiplicationButton,
                fourButton,
                fiveButton,
                sixButton,
                subtractionButton,
                oneButton,
                twoButton,
                threeButton,
                additionButton,
                zeroButton,
                commaButton,
                equalsButton
        );
        stream.forEach(frame.getContentPane()::add);

        frame.setVisible(true);
    }

    private static boolean performActionForNumberButton(JLabel resultLabel, String digitOfButton) {
        String numberInString = resultLabel.getText();

        String firstNumberInString = String.valueOf(firstNumber);
        String[] firstNumberParts = String.valueOf(firstNumber).split("\\.");

        Integer remainderOfFirstNumber = null;

        if (firstNumberParts.length > 1) {
            remainderOfFirstNumber = Integer.parseInt(firstNumberParts[1]);
        }
        if (remainderOfFirstNumber != null && remainderOfFirstNumber == 0) {
            firstNumberInString = firstNumberInString.replaceFirst("\\.\\d", "");
        }


        if (numberInString.equals("0") || numberInString.equals(firstNumberInString)) {
            resultLabel.setText(digitOfButton);
        } else {
            numberInString += digitOfButton;
            resultLabel.setText(numberInString);
        }

        return true;
    }

    private static JFrame createManeFrame() {
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(230, 360));
        frame.pack();
        frame.setLayout(null);
        frame.setResizable(false);

        return frame;
    }

    private static JLabel createLabel() {
        JLabel resultLabel = new JLabel("0");
        resultLabel.setSize(new Dimension(220, 45));
        resultLabel.setVisible(true);
        resultLabel.setLocation(new Point(0, 5));
        resultLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        resultLabel.setFont(new Font("System", Font.PLAIN, 45));

        return resultLabel;
    }

    private static <T> JButton createButton(String buttonText, Point location, Supplier<T> action) {
        JButton button = new JButton(buttonText);
        button.addActionListener((actionEvent) -> action.get());
        button.setLocation(location);
        button.setSize(new Dimension(61, 61));
        button.setVisible(true);

        return button;
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        createGUI();
    }
}
