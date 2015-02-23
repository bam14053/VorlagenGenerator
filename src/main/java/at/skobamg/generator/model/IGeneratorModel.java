package at.skobamg.generator.model;

import java.util.ArrayList;
import java.util.HashMap;

public interface IGeneratorModel {
	void addSnippets(HashMap<String, ISnippet> sippets);
	boolean addSnippet(String name, ISnippet snippet);
	boolean deleteSnippet(String name);
	ISnippet getSnippet(String name);
	HashMap<String, ISnippet> getAllSnippets();
}
