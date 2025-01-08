package org.conagua.common.model.entity;

public class Result<T, E> {
  boolean ok;
  T okValue;
  E errValue;

  private Result(boolean ok, T okValue, E errValue) {
    this.ok = ok;
    this.okValue = okValue;
    this.errValue = errValue;
  }

  public static <T, E> Result<T, E> ok(T value) {
    if (value == null)
      throw new NullPointerException();

    return new Result<T, E>(true, value, null);
  }

  public static <T, E> Result<T, E> err(E value) {
    if (value == null)
      throw new NullPointerException();

    return new Result<T, E>(true, null, value);
  }

  public boolean isOk() {
    return ok;
  }

  public boolean isErr() {
    return !ok;
  }

  public T unwrapOk() {
    if (okValue == null)
      throw new NullPointerException();

    return okValue;
  }

  public E unwrapErr() {
    if (errValue == null)
      throw new NullPointerException();

    return errValue;
  }
}
