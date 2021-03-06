package net.dms.fsync.swing.models;

import net.dms.fsync.synchronizer.fenix.entities.FenixAcc;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.AccStatus;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.TableColumnEnumType;
import net.dms.fsync.swing.components.MyColors;
import net.dms.fsync.swing.components.JenixTableModel;

import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by dminanos on 19/05/2017.
 */
public class AccTableModel extends JenixTableModel<FenixAcc, AccTableModel.Columns> {

    public enum Columns implements TableColumnEnumType{

        ID_ACC(false, 70),
        NOMBRE(true, 250),
        CODIGO_PETICION_CLIENTE(true, 80),
        ESTADO(true, 80),
        JIRA_STATUS(true, 50),
        TIPO(true, 80),
        SUB_TIPO(true, 80),
        ESFUERZO(true, 80),
        INCURRIDO(false, 80),
        PUNTOS_HISTORIA(true, 80),
        HISTORIA_USUARIO(true, 80),

        ETC(true, 50),
        PORCENTAJE_COMPLETADO(true, 30),
        RESPONSABLE(true, 100),
        FECHA_PREVISTA_PROYECTO(true, 80),
        DESCRIPCION(true, 250),
        ULTIMA_BITACORA(false, 50),
        BITACORA(false, 250);


        private final static int WIDTH_M = 80;
        private boolean editable;
        private int width;

        Columns(boolean editable, int width) {
            this.editable = editable;
            this.width = width;
        }

        public boolean isEditable() {
            return editable;
        }

        public int getWidth(){
            return width;
        }

        public static Columns lookup(int iPosition){
            return Arrays.stream(Columns.values()).filter( c -> c.ordinal() == iPosition).findFirst().get();
        }
    }

    public AccTableModel(List<FenixAcc> accs){
        super(Columns.class, accs);
    }



    public boolean isCellEditable(int row, int col)
    {
        FenixAcc acc = rows.get(row);

        return Columns.lookup(col).isEditable() && (AccStatus.EN_EJECUCION.getDescription().equals(acc.getEstado()) || AccStatus.PENDIENTE_ASIGNACION.getDescription().equals(acc.getEstado()));

    }

    public Object getValueAt(int row, int col) {
        FenixAcc acc = rows.get(row);
        switch (Columns.lookup(col)) {
            case BITACORA:
                if (!acc.getBitacora().isEmpty()){
                    return acc.getBitacora().get(acc.getBitacora().size()-1).getComment();
                } else {
                    return null;
                }
            case ULTIMA_BITACORA:
                if (!acc.getBitacora().isEmpty()){
                    return acc.getBitacora().get(acc.getBitacora().size()-1).getCreationDate();
                } else {
                    return null;
                }
            default:
                return super.getValueAt(row, col);
        }

    }


    public void setValueAt(Object value, int row, int col) {
        FenixAcc acc = rows.get(row);
        switch (Columns.lookup(col)){
            case ESFUERZO:
                acc.setEsfuerzo((String)value);
                acc.setEsfuerzoCliente(acc.getEsfuerzo());
                fireTableCellUpdated(row, col);
                break;
            case ESTADO:
                acc.setEstado((String)value);
                if (AccStatus.ENTREGADA.getDescription().equals(acc.getEstado()) && acc.getFechaEntrega() == null){
                    acc.setFechaEntrega(new Date());
                }
                fireTableCellUpdated(row, col);
                break;
            default:
                super.setValueAt(value, row, col);

        }
    }

    public Color getRowColour(int row) {

        FenixAcc acc = rows.get(row);
        if (AccStatus.ENTREGADA.getDescription().equals(acc.getEstado())
                ||AccStatus.DESESTIMADA.getDescription().equals(acc.getEstado())
                ||AccStatus.CERRADA.getDescription().equals(acc.getEstado())){
            return MyColors.ACC_NO_EDITABLE;
        }else{
            return super.getRowColour(row);
        }

    }



}