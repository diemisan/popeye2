package net.dms.popeye.handlers.jfsynchronizer.swing.components;

import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations.VisualizableType;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;


/**
 * Created by dminanos on 22/05/2017.
 */



public class SwingUtil {


    public final static int ALTO_FILA_DEFAULT = 25;

    public static <C,D> List<SelectOption<C,D>> mapToSelectOptionList(Map<C,D> map){
        List<SelectOption<C,D>> list = new ArrayList<>();
        for  (Map.Entry<C,D> e : map.entrySet()){
            list.add(new SelectOption<C, D>(e.getKey(), e.getValue()));
        }
        return list;
    }


    public static void loadComboBox(Class<? extends VisualizableType> vt, JComboBox c, boolean includeEmpty){
        if (includeEmpty){
            c.addItem("");
        }
        for (VisualizableType e : vt.getEnumConstants()){
            c.addItem(e.getDescription());
        }
    }

    public static void loadComboBox(Collection list, JComboBox c, boolean includeEmpty){
        if (includeEmpty){
            c.addItem("");
        }
        for (Object s : list){
            c.addItem(s);
        }
    }



    public static void marcarObligatorio(JComponent component) {
        ponerNegrita(component);
    }

    public static void ponerNegrita(JComponent component) {
        Font fontObligatorio = new Font(component.getFont().getFontName(),
                Font.BOLD, component.getFont().getSize());
        component.setFont(fontObligatorio);
    }



    // public static void redimensionarSegunPantalla(Component component, double
    // width, double height){
    // Dimension dim = component.getToolkit().getScreenSize();
    // dim.setSize(dim.getWidth()*width, dim.getHeight()*height);
    // component.setSize(dim);
    //
    // }
    public static void msgError(Exception ee) {
        JOptionPane.showMessageDialog(null, ee.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
    }



    public static void msgErrorTxt(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void msgWarnTxt(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Aviso",
                JOptionPane.WARNING_MESSAGE);
    }

    public static void msgInfoTxt(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }




    public static void configurarBotonSeleccion(JButton button) {
        button.setIcon(new ImageIcon("resources/img/search20.gif"));
    }

    public static void configurarBotonMostrar(JButton button) {
        button.setIcon(new ImageIcon("resources/img/eye20x20.png"));
        // button.setText(">");
    }

    public static void configurarTabla(final JTable tabla, JMenuItem[] menus) {
        tabla.setRowHeight(ALTO_FILA_DEFAULT);
        TableColumn column = null;

        column = tabla.getColumnModel().getColumn(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);

        column.setResizable(false);
        column = tabla.getColumnModel().getColumn(1);
        column.setMinWidth(50);
        column.setMaxWidth(50);
        // column.setPreferredWidth(50);
        tabla.setFillsViewportHeight(true);
        tabla.setAutoCreateRowSorter(true);
     //   tabla.setDefaultRenderer(BigDecimal.class, new DecimalCellRenderer());
        // tabla.setForeground(new Color(0xE6E6E6));

        if (menus == null) {
            agregarMenu(tabla, new JMenuItem[] { menuCopiar(tabla) });
        } else {
            agregarMenu(tabla, menus);
        }
    }

    public static void configurarTabla(final JTable tabla) {
        configurarTabla(tabla, (JMenuItem[])null);
    }

    public static void agregarMenu(final JTable tabla, JMenuItem[] menus) {
        tabla.addMouseListener(new PopClickListener(menus));
    }

    public static JMenuItem menuCopiar(final JTable tabla) {
        JMenuItem menuCopiar = new JMenuItem("Copiar");
        menuCopiar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    javax.swing.table.TableModel tm = tabla.getModel();
                    int[] selected = tabla.getSelectedRows();
                    StringBuilder sb = new StringBuilder();
                    for (int row : selected) {

                        for (int col = 1; col < tabla.getColumnCount(); col++) {
                            if (tm.getValueAt(row, col) == null) {
                                sb.append("");
                            } else {
                                sb.append(tm.getValueAt(row, col));
                            }
                            sb.append(";");
                        }
                        sb.append("\n");
                    }

                    StringSelection data = new StringSelection(sb.toString());
                    Clipboard clipboard = Toolkit.getDefaultToolkit()
                            .getSystemClipboard();
                    clipboard.setContents(data, data);
                } catch (Exception ex) {
                    //logger.error("error copiando", ex);
                }

            }

        });
        return menuCopiar;
    }



    public static void marcarEditable(JTextComponent comp, boolean editable) {
        if (editable) {
            comp.setEditable(editable);
            comp.setBackground(Color.WHITE);
        } else {
            comp.setEditable(editable);
            comp.setBackground(new Color(0xE6E6E6));
        }
    }



    public static void resize(Component comp, float w, float h) {
        Dimension dim = new Dimension((int) (comp.getToolkit().getScreenSize()
                .getWidth() * w), (int) (comp.getToolkit().getScreenSize()
                .getHeight() * h));
        comp.setPreferredSize(dim);
    }



}
