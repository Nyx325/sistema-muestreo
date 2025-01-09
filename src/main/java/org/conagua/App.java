package org.conagua;

import java.util.List;
import java.util.Optional;

import org.conagua.common.controller.IController;
import org.conagua.common.model.entity.Result;
import org.conagua.common.model.entity.Search;
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

    System.out.println("ELIMINAR POR ID (LOGICO)");
    r2 = ctrl.delete(s);
    if (r2.isErr()) {
      String err = r2.unwrapErr();
      System.err.println("ERROR: " + err);
    }

    System.out.println("Signatario: " + ctrl.get(s.getId()).get());

    System.out.println("BUSCAR POR CRITERIO");
    SignataryCriteria criteria = new SignataryCriteriaBuilder()
        .active(false)
        .firstNameLike("OM")
        .build();

    Search<ISignatary, SignataryCriteria> search = ctrl.getBy(criteria, 1);
    if (search.getResult().isPresent()) {
      List<ISignatary> searchResult = search.getResult().get();
      for (ISignatary iSignatary : searchResult) {
        System.out.println(iSignatary);
      }
    } else {
      System.out.println("No se encontraron resultados con su busqueda");
    }
  }
}
