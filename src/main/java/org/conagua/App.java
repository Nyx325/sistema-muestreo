package org.conagua;

import java.util.*;
import org.conagua.common.model.entity.*;
import org.conagua.units.controller.*;
import org.conagua.units.model.entity.*;
import org.conagua.units.model.repository.*;

import org.conagua.common.controller.IController;
import org.conagua.common.model.configs.TestConfig;
import org.conagua.common.model.repository.IRepository;

public class App {
  IController<IUnit, INewUnit, UnitCriteria> ctrl;

  public App() {
    IRepository<IUnit, UnitCriteria> repo;
    repo = new UnitSQLiteRepository(TestConfig.getInstance());
    this.ctrl = new UnitController(repo);
  }

  public void print() throws Exception {
    System.out.println("MOSTRANDO TODOS LOS REGISTROS");
    UnitCriteria criteria = new UnitCriteria();

    Search<IUnit, UnitCriteria> search = ctrl.getBy(criteria, 1);
    if (search.getResult().isPresent()) {
      List<IUnit> searchResult = search.getResult().get();
      for (IUnit iStdnatary : searchResult) {
        System.out.println(iStdnatary);
      }
    } else {
      System.out.println("No se encontraron resultados con su busqueda");
    }
  }

  public void probar() throws Exception {
    print();
    List<IUnit> nuevos = agregar();
    if (nuevos.isEmpty()) {
      System.err.println("No se pudieron agregar nuevos registros. Abortando.");
      return;
    }
    IUnit obj = nuevos.get(0);
    modificar(obj);
    eliminarPorId(obj);
  }

  public List<IUnit> agregar() throws Exception {
    System.out.println("AGREGAR");
    List<IUnit> nuevos = new ArrayList<>();
    List<INewUnit> agregados = new ArrayList<>();

    agregados.add(
        new NewUnit(
            "miligramos sobre litro",
            "mg/L",
            WindowType.TIPO1,
            Optional.empty()));

    agregados.add(
        new NewUnit(
            "Unidades de Toxicidad",
            "U.T.",
            WindowType.TIPO1,
            Optional.empty()));

    agregados.add(
        new NewUnit(
            "Unidades de Toxicidad",
            "U.T.",
            WindowType.TIPO1,
            Optional.empty()));

    for (INewUnit agregar : agregados) {
      System.out.println("Intentando agregar: " + agregar);
      Result<IUnit, String> result = ctrl.add(agregar);
      if (result.isErr()) {
        System.err.print("\nERROR: " + result.unwrapErr() + "\n");
      } else {
        IUnit nuevo = result.unwrapOk();
        System.out.println("SE AGREGÓ: " + nuevo);
        nuevos.add(nuevo);
      }
    }

    print();
    System.out.println();
    return nuevos;
  }

  public void modificar(IUnit obj) throws Exception {
    System.out.println("MODIFICANDO: " + obj);
    obj.setLongName("ajdsladjlasdj");
    Result<Void, String> result = ctrl.update(obj);
    if (result.isErr()) {
      System.err.print("\nERROR: " + result.unwrapErr() + "\n");
    } else {
      System.out.println("Se modificó: \n" + obj);
    }
    System.out.println();
  }

  public void eliminarPorId(IUnit obj) throws Exception {
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

  public void eliminar(IUnit obj) throws Exception {
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
    Search<IUnit, UnitCriteria> search;
    UnitCriteria criteria = new UnitCriteriaBuilder()
        .longNameLike("to")
        .build();

    search = ctrl.getBy(criteria, 1);

    if (search.getResult().isPresent()) {
      List<IUnit> results = search.getResult().get();
      for (IUnit result : results) {
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
