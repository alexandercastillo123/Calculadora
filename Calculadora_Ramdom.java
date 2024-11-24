import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Calculadora_Ramdom extends JFrame implements ActionListener {
    private JTextField pantalla;
    private double primerNumero, segundoNumero, resultado;
    private String operador;

    public Calculadora_Ramdom() {
        setTitle("Calculadora Básica");
        setSize(250, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5)); // Margen reducido entre bordes y pantalla

        // Panel para la pantalla con bordes reducidos
        JPanel panelPantalla = new JPanel(new BorderLayout());
        panelPantalla.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        panelPantalla.setPreferredSize(new Dimension(300, 50)); // Tamaño de la pantalla reducido

        // Pantalla de la calculadora dentro del panel de pantalla
        pantalla = new JTextField();
        pantalla.setEditable(false);
        pantalla.setFont(new Font("Arial", Font.BOLD, 25));
        pantalla.setHorizontalAlignment(JTextField.RIGHT); // Alineación a la derecha
        pantalla.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Margen interno más pequeño
        panelPantalla.add(pantalla, BorderLayout.CENTER);

        add(panelPantalla, BorderLayout.NORTH);

        // Panel de botones con GridBagLayout para un control preciso
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 3, 3, 3); // Márgenes pequeños entre botones

        // Botones de la calculadora
        String[][] botones = {
            {"CE", "C", "/", "*"},
            {"7", "8", "9", "-"},
            {"4", "5", "6", "+"},
            {"1", "2", "3", "="},
            {"0", ".", ""}
        };

        // Lista para almacenar los botones
        List<String> listaBotones = new ArrayList<>();
        for (String[] fila : botones) {
            Collections.addAll(listaBotones, fila);
        }

        // Reordenar los botones aleatoriamente
        Collections.shuffle(listaBotones);

        // Agregar botones al panel con posición aleatoria
        int index = 0;
        for (int fila = 0; fila < botones.length; fila++) {
            for (int col = 0; col < botones[fila].length; col++) {
                String texto = listaBotones.get(index++);
                if (!texto.equals("")) {
                    JButton boton = new JButton(texto);
                    boton.setFont(new Font("Arial", Font.BOLD, 18));
                    boton.addActionListener(this);

                    // Configurar posicionamiento especial para "0" y "="
                    if (texto.equals("0")) {
                        gbc.gridx = col;
                        gbc.gridy = fila;
                        gbc.gridwidth = 3; // El "0" ocupa tres columnas
                        panel.add(boton, gbc);
                        col += 2; // Saltar las siguientes dos columnas
                    } else if (texto.equals("=")) {
                        gbc.gridx = col;
                        gbc.gridy = fila;
                        gbc.gridheight = 2; // El "=" ocupa dos filas
                        panel.add(boton, gbc);
                        gbc.gridheight = 1; // Resetear altura para el resto
                    } else {
                        gbc.gridx = col;
                        gbc.gridy = fila;
                        gbc.gridwidth = 1; // Configuración estándar
                        panel.add(boton, gbc);
                    }
                }
            }
        }

        // Agregar panel de botones al centro sin margen extra
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Reducir margen del borde del panel
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
        } else if (comando.matches("[/*\\-+]")) {
            if (!pantalla.getText().isEmpty()) {
                primerNumero = Double.parseDouble(pantalla.getText());
                operador = comando;
                pantalla.setText("");
            }
        } else if (comando.equals("=")) {
            if (!pantalla.getText().isEmpty()) {
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
            }
        }
    }

    public static void main(String[] args) {
        new Calculadora_Ramdom();
    }
}
