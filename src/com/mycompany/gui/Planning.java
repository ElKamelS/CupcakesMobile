/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.l10n.DateFormat;
import com.codename1.l10n.DateFormatPatterns;
import com.codename1.l10n.L10NManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Calendar;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.spinner.Picker;
import com.mycompany.Services.PlanningService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


/**
 *
 * @author berrahal
 */
public class Planning {
  Form f;

    Button btnaff;
                            Calendar c = new Calendar();
    public Planning() {
                f = new Form("Planning Affecté");
                PlanningService ps = new PlanningService();
                       ArrayList<com.mycompany.Entities.Planning> ls = new ArrayList<com.mycompany.Entities.Planning>();
                       ls=ps.getAllPlannings();
                               Container c3 = new Container(BoxLayout.y());
L10NManager man = L10NManager.getInstance();
man.setLocale(man.getLocale(), "fr");
                           Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);

                    for (com.mycompany.Entities.Planning l : ls) {
                        if (l.getLine_order()!=0){
                
                         Label start = new Label("De "+man.formatDateTimeMedium(l.getDatestart()));
                         Label end = new Label("Au "+man.formatDateTimeMedium(l.getDateend()));

                         start.getUnselectedStyle().setFont(font);
                         end.getUnselectedStyle().setFont(font);

                             Container c1 = new Container(BoxLayout.y());
            Container c2 = new Container(BoxLayout.x());
            c1.add(start);
            c1.add(end);
                    f.add(c1);
                        }
               
        }
                  
                   


    }
    
     public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
}
