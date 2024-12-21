package org.example.demo.core.implementations;

import org.example.demo.core.Views;

import java.util.Scanner;

public abstract class ViewsImpl<T> implements Views<T> {
    protected final Scanner scanner = new Scanner(System.in);
}