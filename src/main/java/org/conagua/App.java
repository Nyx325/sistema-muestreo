package org.conagua;

import java.util.Optional;

import org.conagua.common.controller.IController;
import org.conagua.common.model.entity.Result;
import org.conagua.signataries.controller.SignataryController;
import org.conagua.signataries.model.entity.*;

public class App {
  public static void main(String[] args) throws Exception {
    IController<ISignatary, INewSignatary, SignataryCriteria> ctrl = new SignataryController();

    System.out.println("AGREGAR");
    NewSignatary ns = new NewSignatary("Rubén", "Román");
    Result<ISignatary, String> result = ctrl.add(ns);

    if (result.isErr()) {
      System.err.println("ERROR: " + result.unwrapErr());
      return;
    }

    ISignatary iSig = result.unwrapOk();

    System.out.println("BUSCAR POR ID");
    Optional<ISignatary> sOpt = ctrl.get(iSig.getId());

    if (!sOpt.isPresent()) {
      System.out.println("No se encontró el Signatario");
    } else {
      ISignatary s = sOpt.get();
      System.out.println(s);
    }

    System.out.println("ACTUALIZAR");
    Signatary s = Signatary.of(iSig);
    s.setFirstName("Omar");
    s.setMotherLastname(Optional.of("Salinas"));

    Result<Void, String> r2 = ctrl.update(s);
    if (r2.isErr()) {
      String err = r2.unwrapErr();
      System.err.println("ERROR: " + err);
    }

    System.out.println("Eliminar por ID (Logico)");
    r2 = ctrl.delete(s);
    if (r2.isErr()) {
      String err = r2.unwrapErr();
      System.err.println("ERROR: " + err);
    }

  }
}
