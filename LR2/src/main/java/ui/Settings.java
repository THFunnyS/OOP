package ui;

import functions.factory.TabulatedFunctionFactory;

public class Settings {
    public static Settings sets;
    private TabulatedFunctionFactory factory;

    private Settings() {
    }

    public static Settings getInstance() {
        if (sets == null) sets = new Settings();
        return sets;
    }

    public TabulatedFunctionFactory getFactory() {
        return factory;
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }
}
