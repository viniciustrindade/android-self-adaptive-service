package br.com.vt.mapek.bundles.loopmng;

import br.com.vt.mapek.services.domain.SystemProperty;

public class Constants {

	public static String filename = "loops.xml";
	public static String[] bundlesToStart = { "rb_spell_services.bundle",
			"rb_spell_checker.bundle", "rb_spell_checker_gui.bundle", "rb_spell_english.bundle",
			"rb_spellcheckgui_for_android.bundle" };
	public static String[] bundlesToStop = { "rb_spell_checker_gui.bundle",
			"rb_spellcheckgui_for_android.bundle" };
	public static SystemProperty property = SystemProperty.BATERIA;
}
