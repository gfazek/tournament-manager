/*7 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.gergofazekas.web.util;

import hu.unideb.gergofazekas.entity.TournamentEntity;
import hu.unideb.gergofazekas.service.TournamentServiceLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 *
 * @author gfazekas
 */
@Named
@RequestScoped
public class TournamentConverter implements Converter{

    private static final Logger logger
            = Logger.getLogger("util.TournamentConverter");
    
    @EJB
    private TournamentServiceLocal tournamentServiceLocal;
    
    public TournamentConverter() {
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        logger.log(Level.INFO, "TournamentConverter getAsObject called with String value: called with {0}: ", value);
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            Long id = Long.valueOf(value);
            logger.log(Level.INFO, "TournamentServiceLocal is {0}: ", tournamentServiceLocal);
            return tournamentServiceLocal.findTournament(id);
        } catch (NumberFormatException e) {
            throw new ConverterException("The value is not a valid Tournament ID: " + value, e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        logger.log(Level.INFO, "TournamentConverter getAsSttring called with Object value: {0}", value);
        if (value == null) {
            return "";
        }
        if (value instanceof TournamentEntity) {
            Long id = ((TournamentEntity) value).getId();
            return (id != null) ? String.valueOf(id) : null;
        } else {
            throw new ConverterException("The value is not a valid Tournament instance: " + value);
        }
    }
}
