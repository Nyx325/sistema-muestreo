package org.conagua;

import java.util.UUID;

import org.conagua.common.controller.IController;
import org.conagua.common.model.entity.IEntity;
import org.conagua.common.model.entity.Search;
import org.conagua.signataries.controller.SignataryController;
import org.conagua.signataries.model.entity.Signatary;
import org.conagua.signataries.model.entity.SignataryCriteria;

public class App {
  public static void main(String[] args) throws Exception {
    try {
      IController ctrl = new SignataryController();
      UUID id = UUID.randomUUID();

      System.out.println("AGREGAR");
      ctrl.add(new Signatary(id, "Ruben", "Omar", "Roman", "Salinas"));

      System.out.println("BUSCAR POR ID");
      Signatary signatary = Signatary.parseSignatary(ctrl.get(id));
      System.out.println(signatary);

      System.out.println("MODIFICAR");
      signatary.setFirstName("Rubiño");
      signatary.setFatherLastname("Ramón");
      ctrl.update(signatary);
      signatary = Signatary.parseSignatary(ctrl.get(id));
      System.out.println(signatary);

      System.out.println("ELIMINAR");
      ctrl.delete(signatary);
      signatary = Signatary.parseSignatary(ctrl.get(id));
      System.out.println(signatary);

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
