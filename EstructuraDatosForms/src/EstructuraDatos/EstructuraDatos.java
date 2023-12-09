package EstructuraDatos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class EstructuraDatos extends JFrame {
    private static final long serialVersionUID = 1L;
    private Queue<String> tareasPendientes;
    private List<String> tareasCompletadas;
    private PriorityQueue<String> arbolPrioridad;
    private DefaultListModel<String> listModel;

    public EstructuraDatos() {
        setTitle("Gestión de Tareas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tareasPendientes = new LinkedList<>();
        tareasCompletadas = new LinkedList<>();
        arbolPrioridad = new PriorityQueue<>();
        listModel = new DefaultListModel<>();

        JList<String> dataList = new JList<>(listModel);

        JButton agregarTareaBtn = new JButton("Agregar tarea pendiente (Cola)");
        agregarTareaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevaTarea = JOptionPane.showInputDialog("Ingrese una nueva tarea:");
                if (nuevaTarea != null && !nuevaTarea.isEmpty()) {
                    tareasPendientes.offer(nuevaTarea);
                    updateList();
                }
            }
        });

        JButton completarTareaBtn = new JButton("Completar tarea (Cola y Lista)");
        completarTareaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tareasPendientes.isEmpty()) {
                    String tareaCompletada = tareasPendientes.poll();
                    tareasCompletadas.add(tareaCompletada);
                    updateList();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay tareas pendientes para completar.");
                }
            }
        });

        JButton verTareasPendientesBtn = new JButton("Ver tareas pendientes (Cola)");
        verTareasPendientesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Tareas pendientes: " + tareasPendientes.toString());
            }
        });

        JButton verTareasCompletadasBtn = new JButton("Ver tareas completadas (Lista)");
        verTareasCompletadasBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Tareas completadas: " + tareasCompletadas.toString());
            }
        });

        JButton agregarTareaPrioridadBtn = new JButton("Agregar tarea al árbol de prioridad");
        agregarTareaPrioridadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevaTarea = JOptionPane.showInputDialog("Ingrese una nueva tarea:");
                if (nuevaTarea != null && !nuevaTarea.isEmpty()) {
                    arbolPrioridad.offer(nuevaTarea);
                    updateList();
                }
            }
        });

        JButton verArbolPrioridadBtn = new JButton("Ver árbol de prioridad");
        verArbolPrioridadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Árbol de prioridad: " + arbolPrioridad.toString());
            }
        });

        JButton agregarTareaGrafoBtn = new JButton("Agregar tarea al grafo de tareas");
        agregarTareaGrafoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarTareaAlGrafo();
            }
        });

        JButton verGrafoBtn = new JButton("Ver grafo de tareas (BFS)");
        verGrafoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tareaInicial = JOptionPane.showInputDialog("Ingrese la tarea inicial:");
                if (tareaInicial != null && !tareaInicial.isEmpty()) {
                    GrafoTareasManager grafoManager = new GrafoTareasManager();
                    // Construir el grafo aquí con los datos ingresados por el usuario
                    // (puedes usar un cuadro de diálogo similar para ingresar relaciones entre tareas)
                    // Ejemplo de datos de prueba:
                    grafoManager.agregarTarea("Tarea A", Arrays.asList("Tarea B", "Tarea C"));
                    grafoManager.agregarTarea("Tarea B", Arrays.asList("Tarea D"));
                    grafoManager.agregarTarea("Tarea C", Arrays.asList("Tarea E"));
                    grafoManager.agregarTarea("Tarea D", Collections.emptyList());
                    grafoManager.agregarTarea("Tarea E", Collections.emptyList());

                    // Ver el grafo utilizando BFS desde la Tarea A
                    JOptionPane.showMessageDialog(null, "Recorrido BFS del grafo desde la Tarea " + tareaInicial + ":");
                    grafoManager.verGrafoBFS(tareaInicial);
                }
            }
        });

        JButton salirBtn = new JButton("Salir");
        salirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(agregarTareaBtn);
        getContentPane().add(completarTareaBtn);
        getContentPane().add(verTareasPendientesBtn);
        getContentPane().add(verTareasCompletadasBtn);
        getContentPane().add(agregarTareaPrioridadBtn);
        getContentPane().add(verArbolPrioridadBtn);
        getContentPane().add(agregarTareaGrafoBtn);
        getContentPane().add(verGrafoBtn);
        getContentPane().add(salirBtn);
        getContentPane().add(new JScrollPane(dataList));

        setVisible(true);
    }

    private void updateList() {
        listModel.clear();
        listModel.addElement("Tareas Pendientes (Cola): " + tareasPendientes.toString());
        listModel.addElement("Tareas Completadas (Lista): " + tareasCompletadas.toString());
        listModel.addElement("Árbol de Prioridad: " + arbolPrioridad.toString());
        // Puedes agregar información de otras estructuras de datos aquí
    }

    // Clase para el árbol de prioridad
    class GrafoTareasManager {
        private Map<String, List<String>> grafo;

        public GrafoTareasManager() {
            grafo = new HashMap<>();
        }

        public void agregarTarea(String tarea, List<String> tareasRelacionadas) {
            grafo.put(tarea, new ArrayList<>(tareasRelacionadas));
        }

        public void verGrafoBFS(String tareaInicial) {
            Set<String> visitados = new HashSet<>();
            Queue<String> cola = new LinkedList<>();

            cola.offer(tareaInicial);
            visitados.add(tareaInicial);

            while (!cola.isEmpty()) {
                String tareaActual = cola.poll();
                System.out.println(tareaActual);

                for (String tareaRelacionada : grafo.getOrDefault(tareaActual, Collections.emptyList())) {
                    if (!visitados.contains(tareaRelacionada)) {
                        cola.offer(tareaRelacionada);
                        visitados.add(tareaRelacionada);
                    }
                }
            }
        }
    }

    private void agregarTareaAlGrafo() {
        String nuevaTarea = JOptionPane.showInputDialog("Ingrese el nombre de la nueva tarea:");
        if (nuevaTarea != null && !nuevaTarea.isEmpty()) {
            // Puedes solicitar al usuario que ingrese las tareas relacionadas y construir la lista
            String tareasRelacionadasStr = JOptionPane.showInputDialog("Ingrese las tareas relacionadas (separadas por comas):");
            List<String> tareasRelacionadas = new ArrayList<>();
            if (tareasRelacionadasStr != null && !tareasRelacionadasStr.isEmpty()) {
                String[] tareasArray = tareasRelacionadasStr.split(",");
                Collections.addAll(tareasRelacionadas, tareasArray);
            }

            // Agregar la tarea al grafo
            GrafoTareasManager grafoManager = new GrafoTareasManager();
            grafoManager.agregarTarea(nuevaTarea, tareasRelacionadas);
            JOptionPane.showMessageDialog(null, "Tarea agregada al grafo.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EstructuraDatos();
            }
        });
    }
}
