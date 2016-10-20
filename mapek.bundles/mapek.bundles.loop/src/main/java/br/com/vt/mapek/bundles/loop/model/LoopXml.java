package br.com.vt.mapek.bundles.loop.model;

import java.util.LinkedList;
import java.util.List;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "loops")
@Default(DefaultType.FIELD)
public class LoopXml {
	@ElementList
	public List<XLoop> loops;

	public LoopXml() {
		this.loops = new LinkedList<XLoop>();
	}

	public XLoop instance() {
		XLoop lop = new XLoop(XLoop.DEFAULT_RATE);
		this.loops.add(lop);

		return lop;
	}

	public List<XLoop> getLoops() {
		return loops;
	}

}
