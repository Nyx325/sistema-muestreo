package org.conagua.common.model.entity;

/**
 * Clase genérica para manejar resultados que pueden ser exitosos o de error.
 *
 * @param <T> El tipo de valor asociado a un resultado exitoso.
 * @param <E> El tipo de valor asociado a un resultado de error.
 */
public class Result<T, E> {
  private final boolean ok; // Indica si el resultado es exitoso.
  private final T okValue; // Valor asociado con el éxito, puede ser null.
  private final E errValue; // Valor asociado con el error, puede ser null.

  /**
   * Constructor privado para crear una instancia de Result.
   *
   * @param ok       Indica si el resultado es exitoso.
   * @param okValue  Valor asociado al éxito.
   * @param errValue Valor asociado al error.
   */
  private Result(boolean ok, T okValue, E errValue) {
    this.ok = ok;
    this.okValue = okValue;
    this.errValue = errValue;
  }

  /**
   * Crea un resultado exitoso con un valor asociado.
   *
   * @param value El valor asociado con el éxito.
   * @param <T>   El tipo del valor exitoso.
   * @param <E>   El tipo del valor de error.
   * @return Una instancia de Result indicando éxito.
   */
  public static <T, E> Result<T, E> ok(T value) {
    return new Result<>(true, value, null);
  }

  /**
   * Crea un resultado exitoso sin valor asociado.
   *
   * @param <E> El tipo del valor de error.
   * @return Una instancia de Result indicando éxito sin valor.
   */
  public static <E> Result<Void, E> ok() {
    return new Result<>(true, null, null);
  }

  /**
   * Crea un resultado de error con un valor asociado.
   *
   * @param value El valor asociado con el error.
   * @param <T>   El tipo del valor exitoso.
   * @param <E>   El tipo del valor de error.
   * @return Una instancia de Result indicando error.
   * @throws NullPointerException Si el valor del error es null.
   */
  public static <T, E> Result<T, E> err(E value) {
    if (value == null) {
      throw new NullPointerException("El valor del error no puede ser nulo");
    }
    return new Result<>(false, null, value);
  }

  /**
   * Verifica si el resultado es exitoso.
   *
   * @return {@code true} si el resultado es exitoso, {@code false} de lo
   *         contrario.
   */
  public boolean isOk() {
    return ok;
  }

  /**
   * Verifica si el resultado es un error.
   *
   * @return {@code true} si el resultado es un error, {@code false} de lo
   *         contrario.
   */
  public boolean isErr() {
    return !ok;
  }

  /**
   * Obtiene el valor asociado al éxito.
   *
   * @return El valor asociado al éxito.
   * @throws IllegalStateException Si el resultado es un error.
   */
  public T unwrapOk() {
    if (!ok) {
      throw new IllegalStateException("No se puede descomponer un resultado exitoso de un error");
    }

    if (ok && okValue == null)
      throw new IllegalStateException("No se puede descomponer un resultado exitoso de un Result<Void, E>");

    return okValue;
  }

  /**
   * Obtiene el valor asociado al error.
   *
   * @return El valor asociado al error.
   * @throws IllegalStateException Si el resultado es exitoso.
   */
  public E unwrapErr() {
    if (ok) {
      throw new IllegalStateException("No se puede descomponer un resultado de error de un éxito");
    }
    return errValue;
  }
}
