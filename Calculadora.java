import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {
    private JTextField pantalla;
    private double primerNumero, segundoNumero, resultado;
    private String operador;

    public Calculadora() {
        setTitle("Calculadora Básica");
        setSize(250, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        JPanel panelPantalla = new JPanel(new BorderLayout());
        panelPantalla.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        panelPantalla.setPreferredSize(new Dimension(300, 50));

        pantalla = new JTextField();
        pantalla.setEditable(false);
        pantalla.setFont(new Font("Arial", Font.BOLD, 25));
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        pantalla.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pantalla.setForeground(Color.BLACK);
        panelPantalla.add(pantalla, BorderLayout.CENTER);

        add(panelPantalla, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 3, 3, 3);

        String[][] botones = {
            {"CE", "C", "/", "*"},
            {"7", "8", "9", "-"},
            {"4", "5", "6", "+"},
            {"1", "2", "3", "="},
            {"0", ".", ""}
        };

        Color colorBoton = new Color(102, 204, 255);
        Color colorBotonOperador = new Color(255, 165, 0);
        Color colorBotonResultado = new Color(0, 204, 102);

        for (int fila = 0; fila < botones.length; fila++) {
            for (int col = 0; col < botones[fila].length; col++) {
                String texto = botones[fila][col];
                if (!texto.equals("")) {
                    JButton boton = new JButton(texto);
                    boton.setFont(new Font("Arial", Font.BOLD, 18));
                    boton.addActionListener(this);

                    boton.setBackground(colorBoton);
                    boton.setForeground(Color.WHITE);
                    boton.setFocusPainted(false);

                    if (texto.matches("[/*\\-+]")) {
                        boton.setBackground(colorBotonOperador);
                    } else if (texto.equals("=")) {
                        boton.setBackground(colorBotonResultado);
                    } else if (texto.equals("CE") || texto.equals("C")) {
                        boton.setBackground(new Color(255, 77, 77));
                    }

                    if (texto.equals("0")) {
                        gbc.gridx = col;
                        gbc.gridy = fila;
                        gbc.gridwidth = 3;
                        panel.add(boton, gbc);
                        col += 2;
                    } else if (texto.equals("=")) {
                        gbc.gridx = col;
                        gbc.gridy = fila;
                        gbc.gridheight = 2;
                        panel.add(boton, gbc);
                        gbc.gridheight = 1;
                    } else {
                        gbc.gridx = col;
                        gbc.gridy = fila;
                        gbc.gridwidth = 1;
                        panel.add(boton, gbc);
                    }
                }
            }
        }

        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.matches("[0-9.]")) {
            pantalla.setText(pantalla.getText() + comando);
        } else if (comando.equals("CE")) {
            if (pantalla.getText().length() > 0) {
                pantalla.setText(pantalla.getText().substring(0, pantalla.getText().length() - 1));
            }
        } else if (comando.equals("C")) {
            pantalla.setText("");
            primerNumero = 0;
            segundoNumero = 0;
            operador = "";
        } else if (comando.equals("-")) {
            if (pantalla.getText().isEmpty()) {
                pantalla.setText("-");
            } else if (operador == null || operador.isEmpty()) {
                primerNumero = Double.parseDouble(pantalla.getText());
                operador = "-";
                pantalla.setText("");
            }
        } else if (comando.matches("[/*+]")) {
            if (!pantalla.getText().isEmpty()) {
                primerNumero = Double.parseDouble(pantalla.getText());
                operador = comando;
                pantalla.setText("");
            }
        } else if (comando.equals("=")) {
            if (!pantalla.getText().isEmpty() && operador != null) {
                segundoNumero = Double.parseDouble(pantalla.getText());

                switch (operador) {
                    case "+":
                        resultado = primerNumero + segundoNumero;
                        break;
                    case "-":
                        resultado = primerNumero - segundoNumero;
                        break;
                    case "*":
                        resultado = primerNumero * segundoNumero;
                        break;
                    case "/":
                        if (segundoNumero != 0) {
                            resultado = primerNumero / segundoNumero;
                        } else {
                            pantalla.setText("Error");
                            return;
                        }
                        break;
                }
                pantalla.setText(String.valueOf(resultado));
                operador = null;
            }
        }
    }

    public static void main(String[] args) {
        new Calculadora();
    }
}
