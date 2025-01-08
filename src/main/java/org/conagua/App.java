package org.conagua;

import java.util.UUID;

import org.conagua.common.controller.IController;
import org.conagua.common.model.entity.IEntity;
import org.conagua.common.model.entity.INewEntity;
import org.conagua.common.model.entity.Search;
import org.conagua.signataries.model.entity.SignataryCriteria;
import org.conagua.standards.controller.StandardController;
import org.conagua.standards.model.entity.NewStandard;
import org.conagua.standards.model.entity.Standard;

public class App {
  public static void main(String[] args) throws Exception {
    try {
      IController ctrl = new StandardController();
      UUID id = UUID.randomUUID();

      System.out.println("AGREGAR");
      INewEntity newStd = new NewStandard("NMX-AA-014-1980");
      ctrl.add(newStd);

      System.out.println("BUSCAR POR ID");
      Standard std = Standard.parseStandard(ctrl.get(id));
      System.out.println(std);

      System.out.println("MODIFICAR");
      std.setName("NMX-AA-014-1982");
      ctrl.update(std);
      std = Standard.parseStandard(ctrl.get(id));
      System.out.println(std);

      System.out.println("ELIMINAR");
      ctrl.delete(std);
      std = Standard.parseStandard(ctrl.get(id));
      System.out.println(std);

      System.out.println("BUSCAR");
      Search s = ctrl.getBy(new SignataryCriteria("R", null, null, null, null), 1);

      for (IEntity sig : s.getResult()) {
        System.out.println(sig);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
