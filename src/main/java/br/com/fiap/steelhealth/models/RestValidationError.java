package br.com.fiap.steelhealth.models;

public record RestValidationError(String field, String message) {}