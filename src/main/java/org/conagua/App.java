package org.conagua;

import java.util.UUID;

import org.conagua.common.domain.IEntity;
import org.conagua.common.domain.IRepository;
import org.conagua.common.domain.Search;
import org.conagua.signataries.domain.ISignatary;
import org.conagua.signataries.domain.SignataryCriteria;
// import org.conagua.signataries.extern.InMemorySignataryRepository;
import org.conagua.signataries.extern.Signatary;
import org.conagua.signataries.extern.SignataryEbeanRepository;

import io.ebean.DB;

public class App {
  public static void main(String[] args) {
    DB.getDefault();

    IRepository signataryRepo = new SignataryEbeanRepository();
    ISignatary s1 = new Signatary(UUID.randomUUID(), "Rubén", "Román");
    ISignatary s2 = new Signatary(UUID.randomUUID(), "Omar", "Salinas");
    try {
      System.out.println("Agregando");
      signataryRepo.add(s1);
      signataryRepo.add(s2);

      Search search = signataryRepo.getBy(new SignataryCriteria(), 1);
      System.out.println("Busqueda. Resultados: " + search.getResult().size());
      for (IEntity entity : search.getResult()) {
        System.out.println(entity);
      }

      System.out.println("Eliminar");
      signataryRepo.delete(s1);
      signataryRepo.delete(s2);

      Search search2 = signataryRepo.getBy(new SignataryCriteria(), 1);
      System.out.println("Busqueda. Resultados: " + search2.getResult().size());
      for (IEntity entity : search2.getResult()) {
        System.out.println(entity);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
