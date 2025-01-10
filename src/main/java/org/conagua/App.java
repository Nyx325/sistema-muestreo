package org.conagua;

import java.util.ArrayList;
import java.util.List;

import org.conagua.common.controller.IController;
import org.conagua.common.model.configs.TestConfig;
import org.conagua.common.model.entity.ILogicalDeletable;
import org.conagua.common.model.entity.Result;
import org.conagua.common.model.entity.Search;
import org.conagua.common.model.repository.IRepository;
import org.conagua.parameters.model.entity.*;
import org.conagua.parameters.model.repository.*;
import org.conagua.parameters.controller.*;

public class App {
  IController<IParameter, INewParameter, ParameterCriteria> ctrl;

  public App() {
    IRepository<IParameter, ParameterCriteria> repo;
    repo = new ParameterSQLiteRepository(TestConfig.getInstance());
    this.ctrl = new ParameterController(repo);
  }

  public void print() throws Exception {
    System.out.println("MOSTRANDO TODOS LOS REGISTROS");
    ParameterCriteria criteria = new ParameterCriteria();

    Search<IParameter, ParameterCriteria> search = ctrl.getBy(criteria, 1);
    if (search.getResult().isPresent()) {
      List<IParameter> searchResult = search.getResult().get();
      for (IParameter iStdnatary : searchResult) {
        System.out.println(iStdnatary);
      }
    } else {
      System.out.println("No se encontraron resultados con su busqueda");
    }
  }

  public void probar() throws Exception {
    print();
    List<IParameter> nuevos = agregar();
    if (nuevos.isEmpty()) {
      System.err.println("No se pudieron agregar nuevos registros. Abortando.");
      return;
    }
    IParameter obj = nuevos.get(0);
    modificar(obj);
    eliminarPorId(obj);
  }

  public List<IParameter> agregar() throws Exception {
    System.out.println("AGREGAR");
    List<IParameter> nuevos = new ArrayList<>();
    List<INewParameter> agregados = new ArrayList<>();

    agregados.add(new NewParameter("Microbiología"));
    agregados.add(new NewParameter("Metales Pesados"));
    agregados.add(new NewParameter("E-Coli"));

    for (INewParameter agregar : agregados) {
      System.out.println("Intentando agregar: " + agregar);
      Result<IParameter, String> result = ctrl.add(agregar);
      if (result.isErr()) {
        System.err.print("\nERROR: " + result.unwrapErr() + "\n");
      } else {
        IParameter nuevo = result.unwrapOk();
        System.out.println("SE AGREGÓ: " + nuevo);
        nuevos.add(nuevo);
      }
    }

    print();
    System.out.println();
    return nuevos;
  }

  public void modificar(IParameter obj) throws Exception {
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

  public void eliminarPorId(IParameter obj) throws Exception {
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

  public void eliminar(IParameter obj) throws Exception {
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
    Search<IParameter, ParameterCriteria> search;
    ParameterCriteria criteria = new ParameterCriteriaBuilder()
        .nameLike("NOM")
        .build();

    search = ctrl.getBy(criteria, 1);

    if (search.getResult().isPresent()) {
      List<IParameter> results = search.getResult().get();
      for (IParameter result : results) {
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
