package net.dms.fsync.swing.dialogs;

import net.dms.fsync.swing.models.BitacoraTableModel;
import net.dms.fsync.synchronizer.fenix.entities.Bitacora;
import net.dms.fsync.synchronizer.fenix.entities.FenixAcc;
import net.dms.fsync.synchronizer.fenix.entities.FenixResponsable;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.*;
import net.dms.fsync.swing.components.JenixDialog;
import net.dms.fsync.swing.components.JenixTable;
import net.dms.fsync.swing.components.NumberCellEditor;
import net.dms.fsync.swing.components.SwingUtil;
import net.dms.fsync.swing.models.FenixResponsablesTableModel;
import net.dms.fsync.settings.business.SettingsService;
import net.dms.fsync.settings.entities.Actor;
import net.sourceforge.jdatepicker.JDatePicker;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class AccDialog extends JenixDialog<FenixAcc> {
    private JTextField txtNombre;
    private JTextArea txaDescripcion;
    private JTextField txtCodigoPeticionCliente;
    private JComboBox cmbEstado;
    private JComboBox cmbTipo;
   // private JComboBox cmbSubTipo;
    private JTextField txtPuntosHistoria;
    private JTextArea txaComments;
    private JenixTable<BitacoraTableModel, Bitacora> jtbBitacora;

    private JTextField txtHistoriaUsuario;
    private JenixTable<FenixResponsablesTableModel, FenixResponsable> jtbResponsables;
    private JScrollPane responsablesScrollPane;
    private JScrollPane bitacoraScrollPane;
    private JDatePicker datFechaPrevistaProyecto;
    private JTextField txtEsfuerzoCliente;




    public AccDialog(Component parent, FenixAcc initialPayload) {
        super(parent, initialPayload);
        // TODO FIXME, move to abstract class
        this.setSize(550, 750);
        setLocationRelativeTo(parent);
        setTitle("ACC");
        setModal(true);
        setResizable(false);
        setVisible(true);
    }

    @Override
    protected void loadData() {
        SwingUtil.loadComboBox(AccStatus.class, cmbEstado, true);
        SwingUtil.loadComboBox(AccType.class, cmbTipo, true);
      //  SwingUtil.loadComboBox(AccSubType.class, cmbSubTipo, true);
    }

    @Override
    public void edit() {
        txtNombre.setText(getPayload().getNombre());
        txaDescripcion.setText(getPayload().getDescripcion());
        txtCodigoPeticionCliente.setText(getPayload().getCodigoPeticionCliente());
        cmbEstado.setSelectedItem(getPayload().getEstado());
        cmbTipo.setSelectedItem(getPayload().getTipo());
     //   cmbSubTipo.setSelectedItem(getPayload().getSubTipo());
        txtPuntosHistoria.setText(getPayload().getPuntosHistoria());
        txtHistoriaUsuario.setText(getPayload().getHistoriaUsuario());
        txtEsfuerzoCliente.setText(getPayload().getEsfuerzoCliente());
        //jtbBitacora.getModel().load(getPayload().getBitacora());
        List<FenixResponsable> responsablesEsfuerzos = jtbResponsables.getModel().getList();

        for (Actor actor : SettingsService.getInstance().getSettings().getActores()){
            responsablesEsfuerzos.add(new FenixResponsable(null, actor.getNombre(), actor.getNumeroEmpleadoEveris(), null));
        }

        String[] responsables = getPayload().getResponsable() != null ? getPayload().getResponsable().split("-") : new String[0];
        String[] esfuerzos = getPayload().getEsfuerzo() != null ? getPayload().getEsfuerzo().split("-") : new String[0];
        String[] subtiposTareas = getPayload().getSubTipo() != null ? getPayload().getSubTipo().split("-") : new String[0];

        for (int i = 0; i < responsables.length; i++){
            final int index = i;
            FenixResponsable fenixResponsable = responsablesEsfuerzos.stream().filter(r -> r.getNumero().equals(responsables[index])).findFirst().orElse(null);
            if (fenixResponsable != null){
                fenixResponsable.setEsfuerzo(Double.parseDouble(esfuerzos[index]));
                fenixResponsable.setSubtipoTarea(subtiposTareas[index]);
            }else {
                Double esfuerzo = StringUtils.isNotEmpty(esfuerzos[index]) ? Double.parseDouble(esfuerzos[index]) : null;
                fenixResponsable = new FenixResponsable(esfuerzo, null, responsables[index], subtiposTareas[index]);
                responsablesEsfuerzos.add(fenixResponsable);
            }
        }


    }

    @Override
    public JComponent createCentralPanel() {

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(new GridBagLayout());

        JLabel lblSummary = new JLabel("Nombre");
        JLabel lblDescription = new JLabel("Descripción");
        JLabel lblComments = new JLabel("Comentarios seguimiento (No Fenix)");
        JLabel lblCodigoPeticionCliente = new JLabel("Código petición cliente");
        JLabel lblEstado = new JLabel("Estado");
        JLabel lblTipo = new JLabel("Tipo");
        //JLabel lblSubtipo = new JLabel("Subtipo");
        JLabel lblPuntosHistoria = new JLabel("Puntos Historia");
        JLabel lblEsfuerzo = new JLabel("Esfuerzo cliente");
        JLabel lblHistoriaUsuario  = new JLabel("Historia usuario");
        JLabel lblResponsables = new JLabel("Responsables");
/*
        JLabel lblFechaPrevistaProyecto = new JLabel("Fecha prevista");

        JButton addBitacoraBtn = new JButton("Agregar comentario");
        addBitacoraBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtbBitacora.addRow(new Bitacora("-", ""));
            }
        });

*/
        txtCodigoPeticionCliente = new JTextField();
        cmbEstado = new JComboBox();
        cmbTipo = new JComboBox();
       // cmbSubTipo = new JComboBox();
        txtPuntosHistoria = new JTextField();
        txtHistoriaUsuario = new JTextField();
        txtEsfuerzoCliente = new JTextField();

        FenixResponsablesTableModel responsablesTableModel = new FenixResponsablesTableModel(new ArrayList<FenixResponsable>());
        jtbResponsables = new JenixTable(responsablesTableModel);
        responsablesScrollPane = new JScrollPane(jtbResponsables, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        responsablesScrollPane.setMinimumSize(new Dimension(200,120));
        jtbResponsables.setFillsViewportHeight(true);

        responsablesTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                StringBuilder responsables = new StringBuilder();
                StringBuilder esfuerzos = new StringBuilder();
                StringBuilder subtipos = new StringBuilder();
                splitAttributes(responsables, esfuerzos, subtipos);
                txtEsfuerzoCliente.setText(Double.toString(FenixAcc.calculateTotalEsfuerzo(esfuerzos.toString())));
            }
        });
        jtbResponsables.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JComboBox accSubTypeEditor = new JComboBox();
        SwingUtil.loadComboBox(AccSubType.class, accSubTypeEditor, true);

        jtbResponsables.getColumnModel().getColumn(FenixResponsablesTableModel.Columns.ESFUERZO.ordinal()).setCellEditor(new NumberCellEditor());
        jtbResponsables.getColumnModel().getColumn(FenixResponsablesTableModel.Columns.SUBTIPO_TAREA.ordinal()).setCellEditor(new DefaultCellEditor(accSubTypeEditor));

        /*
        BitacoraTableModel bitacoraTableModel = new BitacoraTableModel(new ArrayList<>());
        jtbBitacora = new JenixTable(bitacoraTableModel);
        bitacoraScrollPane = new JScrollPane(jtbBitacora, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        bitacoraScrollPane.setMinimumSize(new Dimension(200,120));
        jtbBitacora.setFillsViewportHeight(true);
*/
       // datFechaPrevistaProyecto = new JDatePickerImpl();

        txtNombre = new JTextField(15);
        txaDescripcion = new JTextArea();
        txaDescripcion.setRows(10);
        txaDescripcion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane sp = new JScrollPane(txaDescripcion, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setPreferredSize(new Dimension(300, 200));
        sp.setMinimumSize(new Dimension(300, 200));
       // sp.setBounds(txtSummary.getBounds());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        int fila = 0;
        double anchoEntiquetas = 0.1;

        constraints.gridx = 0;
        constraints.gridy = fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblSummary, constraints);
        constraints.weightx = 1.0;
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(txtNombre, constraints);

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblDescription, constraints);
        constraints.weightx = 1;

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(sp, constraints);
/*
        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblComments, constraints);
        constraints.weightx = 1;

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(bitacoraScrollPane, constraints);

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(addBitacoraBtn, constraints);
*/
        // fields codigo petición - historia usuario
        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblCodigoPeticionCliente, constraints);
        constraints.weightx = 1;
        constraints.gridwidth = 1;

        constraints.gridx = 1;
        constraints.gridy = fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblHistoriaUsuario, constraints);
        constraints.weightx = 1;


        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(txtCodigoPeticionCliente, constraints);


        constraints.gridx = 1;
        constraints.gridy = fila;
        panel.add(txtHistoriaUsuario, constraints);


        // field
        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblEstado, constraints);
        constraints.weightx = 1;

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(cmbEstado, constraints);

        // field
        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblTipo, constraints);
        constraints.weightx = 1;

     /*   constraints.gridx = 1;
        constraints.gridy = fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblSubtipo, constraints);
        constraints.weightx = 1;
*/
        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(cmbTipo, constraints);


        //constraints.gridx = 1;
       // constraints.gridy = fila;
       // panel.add(cmbSubTipo, constraints);

        // field
        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblPuntosHistoria, constraints);
        constraints.weightx = 1;

        constraints.gridx = 1;
        constraints.gridy = fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblEsfuerzo, constraints);
        constraints.weightx = 1;

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        panel.add(txtPuntosHistoria, constraints);

        constraints.gridx = 1;
        constraints.gridy = fila;
        panel.add(txtEsfuerzoCliente, constraints);




        // field
        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.weightx = anchoEntiquetas;
        panel.add(lblResponsables, constraints);
        constraints.weightx = 1;

        constraints.gridx = 0;
        constraints.gridy = ++fila;
        constraints.gridwidth = 2;
        constraints.gridheight = 5;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(responsablesScrollPane, constraints);



        return panel;
    }

    @Override
    public void fillPayLoad() {
        getPayload().setDescripcion(txaDescripcion.getText());
        getPayload().setNombre(txtNombre.getText());

        getPayload().setCodigoPeticionCliente(txtCodigoPeticionCliente.getText());
        getPayload().setEstado((String)cmbEstado.getSelectedItem());
        getPayload().setTipo((String)cmbTipo.getSelectedItem());
       // getPayload().setSubTipo((String)cmbSubTipo.getSelectedItem());
        getPayload().setPuntosHistoria(txtPuntosHistoria.getText());

        getPayload().setHistoriaUsuario(txtHistoriaUsuario.getText());
        getPayload().setEsfuerzoCliente(txtEsfuerzoCliente.getText());

       // getPayload().setBitacora(jtbBitacora.getList());

        StringBuilder responsables = new StringBuilder();
        StringBuilder esfuerzos = new StringBuilder();
        StringBuilder subtipos = new StringBuilder();
        splitAttributes(responsables, esfuerzos, subtipos);

        getPayload().setEsfuerzo(esfuerzos.toString());
        getPayload().setResponsable(responsables.toString());
        getPayload().setSubTipo(subtipos.toString());

    }

    @Override
    public void initialize() {

    }

    private void splitAttributes(StringBuilder responsables, StringBuilder esfuerzos, StringBuilder subtipos){
        List<FenixResponsable> fenixResponsables = jtbResponsables.getList();

        for (FenixResponsable fenixResponsable : fenixResponsables){
            if (fenixResponsable.getEsfuerzo() != null) {
                if (responsables.length() > 0) {
                    responsables.append("-");
                    esfuerzos.append("-");
                    subtipos.append("-");
                }
                responsables.append(fenixResponsable.getNumero());
                esfuerzos.append(fenixResponsable.getEsfuerzo());
                subtipos.append(fenixResponsable.getSubtipoTarea());
            }
        }
    }


}
