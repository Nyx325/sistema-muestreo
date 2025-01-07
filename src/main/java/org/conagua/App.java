package org.conagua;

import java.util.InputMismatchException;
import java.util.UUID;

import org.conagua.common.model.entity.*;
import org.conagua.common.controller.*;
import org.conagua.signataries.model.entity.*;
import org.conagua.signataries.controller.*;

public class App {
  public static void main(String[] args) {
    IController ctrl = new SignataryController();
    ISignatary s = new Signatary(UUID.randomUUID(), "Maria del", "Carmen", "Ponce de", "León");

    try {
      ctrl.add(s);

      SignataryCriteria criteria = new SignataryCriteria();
      Search lastSearch = ctrl.getBy(criteria, 1);

      for (IEntity entity : lastSearch.getResult()) {
        System.out.printf("Signatario: %s\n", entity);
      }
    } catch (InputMismatchException e) {
      System.err.println("FORMATO INVÁLIDO: " + e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
