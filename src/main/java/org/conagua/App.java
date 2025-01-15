package org.conagua;

import java.util.*;
import org.conagua.common.model.entity.*;
import org.conagua.actions.controller.*;
import org.conagua.actions.model.entity.*;
import org.conagua.actions.model.repository.*;

import org.conagua.common.controller.IController;
import org.conagua.common.model.configs.TestConfig;
import org.conagua.common.model.repository.IRepository;
import org.conagua.parameters.controller.ParameterController;
import org.conagua.parameters.model.entity.INewParameter;
import org.conagua.parameters.model.entity.IParameter;
import org.conagua.parameters.model.entity.NewParameter;
import org.conagua.parameters.model.entity.ParameterCriteria;
import org.conagua.parameters.model.repository.ParameterSQLiteRepository;

public class App {
  IController<IAction, INewAction, ActionCriteria> ctrl;
  IController<IParameter, INewParameter, ParameterCriteria> ctrlParam;

  public App() {
    IRepository<IAction, ActionCriteria> repo;
    IRepository<IParameter, ParameterCriteria> repoParam;

    repo = new ActionSQLiteRepository(TestConfig.getInstance());
    repoParam = new ParameterSQLiteRepository(TestConfig.getInstance());

    this.ctrl = new ActionController(repo, repoParam);
    this.ctrlParam = new ParameterController(repoParam);
  }

  public void print() throws Exception {
    System.out.println("MOSTRANDO TODOS LOS REGISTROS");
    ActionCriteria criteria = new ActionCriteria();

    Search<IAction, ActionCriteria> search = ctrl.getBy(criteria, 1);
    if (search.getResult().isPresent()) {
      List<IAction> searchResult = search.getResult().get();
      for (IAction iStdnatary : searchResult) {
        System.out.println(iStdnatary);
      }
    } else {
      System.out.println("No se encontraron resultados con su busqueda");
    }
  }

  public void probar() throws Exception {
    print();
    List<IAction> nuevos = agregar();
    if (nuevos.isEmpty()) {
      System.err.println("No se pudieron agregar nuevos registros. Abortando.");
      return;
    }
    IAction obj = nuevos.get(0);
    modificar(obj);
    eliminarPorId(obj);
  }

  public List<IAction> agregar() throws Exception {
    List<IAction> nuevos = new ArrayList<>();
    List<INewAction> agregados = new ArrayList<>();

    Result<IParameter, String> res;
    res = this.ctrlParam.add(new NewParameter("Microbiologia"));

    if (res.isErr()) {
      System.out.println("Error al agregar parametro");
      return nuevos;
    }

    IParameter param = res.unwrapOk();
    System.out.println("Se agregó parametro");
    System.out.println(param);

    System.out.println("AGREGAR");

    agregados.add(
        new NewAction(
            "E-Coli",
            Optional.of(param.getId())));

    agregados.add(
        new NewAction(
            "Col. Totales",
            Optional.of(param.getId())));

    agregados.add(
        new NewAction(
            "Col. Totales",
            Optional.of(param.getId())));

    for (INewAction agregar : agregados) {
      System.out.println("Intentando agregar: " + agregar);
      Result<IAction, String> result = ctrl.add(agregar);
      if (result.isErr()) {
        System.err.print("\nERROR: " + result.unwrapErr() + "\n");
      } else {
        IAction nuevo = result.unwrapOk();
        System.out.println("SE AGREGÓ: " + nuevo);
        nuevos.add(nuevo);
      }
    }

    print();
    System.out.println();
    return nuevos;
  }

  public void modificar(IAction obj) throws Exception {
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

  public void eliminarPorId(IAction obj) throws Exception {
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

  public void eliminar(IAction obj) throws Exception {
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
    Search<IAction, ActionCriteria> search;
    ActionCriteria criteria = new ActionCriteriaBuilder()
        .nameLike("ol")
        .build();

    search = ctrl.getBy(criteria, 1);

    if (search.getResult().isPresent()) {
      List<IAction> results = search.getResult().get();
      for (IAction result : results) {
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
