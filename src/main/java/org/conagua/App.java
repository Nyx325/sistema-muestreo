package org.conagua;

import java.util.ArrayList;
import java.util.List;

import org.conagua.common.controller.IController;
import org.conagua.common.model.entity.ILogicalDeletable;
import org.conagua.common.model.entity.Result;
import org.conagua.common.model.entity.Search;
import org.conagua.standard.model.entity.*;
import org.conagua.standard.controller.*;

public class App {
  IController<IStandard, INewStandard, StandardCriteria> ctrl = new StandardController();

  public void print() throws Exception {
    System.out.println("MOSTRANDO TODOS LOS REGISTROS");
    StandardCriteria criteria = new StandardCriteria();

    Search<IStandard, StandardCriteria> search = ctrl.getBy(criteria, 1);
    if (search.getResult().isPresent()) {
      List<IStandard> searchResult = search.getResult().get();
      for (IStandard iStdnatary : searchResult) {
        System.out.println(iStdnatary);
      }
    } else {
      System.out.println("No se encontraron resultados con su busqueda");
    }
  }

  public void probar() throws Exception {
    print();
    List<IStandard> nuevos = agregar();
    if (nuevos.isEmpty()) {
      System.err.println("No se pudieron agregar nuevos registros. Abortando.");
      return;
    }
    IStandard obj = nuevos.get(0);
    modificar(obj);
    eliminarPorId(obj);
  }

  public List<IStandard> agregar() throws Exception {
    System.out.println("AGREGAR");
    List<IStandard> nuevos = new ArrayList<>();
    List<INewStandard> agregados = new ArrayList<>();

    agregados.add(new NewStandard("NOM-133-ADB"));
    agregados.add(new NewStandard("NOM-134-ADB"));
    agregados.add(new NewStandard("NOM-134-ADB"));

    for (INewStandard agregar : agregados) {
      System.out.println("Intentando agregar: " + agregar);
      Result<IStandard, String> result = ctrl.add(agregar);
      if (result.isErr()) {
        System.err.print("\nERROR: " + result.unwrapErr() + "\n");
      } else {
        IStandard nuevo = result.unwrapOk();
        System.out.println("SE AGREGÓ: " + nuevo);
        nuevos.add(nuevo);
      }
    }

    print();
    System.out.println();
    return nuevos;
  }

  public void modificar(IStandard obj) throws Exception {
    System.out.println("MODIFICANDO: " + obj);
    obj.setName("ajdsladjlasdj");
    Result<Void, String> result = ctrl.update(obj);
    if (result.isErr()) {
      System.err.print("\nERROR: " + result.unwrapErr() + "\n");
    } else {
      System.out.println("Se modificó: \n" + obj);
    }
    System.out.println();
  }

  public void eliminarPorId(IStandard obj) throws Exception {
    System.out.println("ELIMINANDO POR ID: " + obj);
    Result<Void, String> result = ctrl.delete(obj.getId());

    if (result.isErr()) {
      System.err.print("\nERROR: " + result.unwrapErr() + "\n");
    } else {
      System.out.println("Se eliminó correctamente.");
    }

    if (obj instanceof ILogicalDeletable) {
      ILogicalDeletable o = (ILogicalDeletable) obj;
      o.setActive(true);
      ctrl.update(obj);
    }
  }

  public void eliminar(IStandard obj) throws Exception {
    System.out.println("ELIMINANDO: " + obj);
    Result<Void, String> result = ctrl.delete(obj);

    if (result.isErr()) {
      System.err.print("\nERROR: " + result.unwrapErr() + "\n");

    } else {
      System.out.println("Se eliminó: " + result.unwrapOk());
    }

    if (obj instanceof ILogicalDeletable) {
      ILogicalDeletable o = (ILogicalDeletable) obj;
      o.setActive(true);
      ctrl.update(obj);
    }
  }

  public void buscar() throws Exception {
    System.out.println("BUSCANDO POR CRITERIO");
    Search<IStandard, StandardCriteria> search;
    StandardCriteria criteria = new StandardCriteriaBuilder()
        .nameLike("NOM")
        .build();

    search = ctrl.getBy(criteria, 1);

    if (search.getResult().isPresent()) {
      List<IStandard> results = search.getResult().get();
      for (IStandard result : results) {
        System.out.println(result);
      }
    } else {
      System.out.println("No se encontraron resultados en su busqueda");
    }
  }

  public static void main(String[] args) throws Exception {
    App app = new App();
    app.probar();
  }
}
