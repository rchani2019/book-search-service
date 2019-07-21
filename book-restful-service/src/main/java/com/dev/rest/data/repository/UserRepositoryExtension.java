package com.dev.rest.data.repository;

public interface UserRepositoryExtension<T> {
	public abstract <S extends T> S save(S paramS);
}
