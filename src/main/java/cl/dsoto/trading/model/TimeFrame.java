package cl.dsoto.trading.model;

import java.io.Serializable;

/**
 * @author Andrés Farías on 8/23/16.
 */
public enum TimeFrame implements Serializable {

    MINUTE(1, "Minute"),
    HOUR(2, "Hour"),
    DAY(3, "Day"),
    WEEK(4, "Week"),
    MONTH(5, "Month");

    /** Identificador único de la base de datos */
    private long id;

    /** Nombre o descripción del cambio */
    private String name;

    TimeFrame(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Este método es responsable de retornar el AuditActionType asociado al ID <code>idAuditActionType</code>.
     *
     * @param timeFrameId El identificador del AuditActionType.
     *
     * @return El objeto que representa la acción de auditoría.
     */
    public static TimeFrame valueOf(long timeFrameId) {
        for (TimeFrame timeFrame : values()) {
            if (timeFrame.getId() == timeFrameId) {
                return timeFrame;
            }
        }

        throw new IllegalArgumentException("No hay un tipo de problema con ID=" + timeFrameId);
    }

}

