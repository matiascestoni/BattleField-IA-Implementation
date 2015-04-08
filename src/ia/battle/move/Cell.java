package ia.battle.move;

import ia.battle.camp.FieldCell;

public class Cell {
	private FieldCell fieldCell;
	private Node node;
	
	public Cell(FieldCell fieldCell, Node node){
		this.fieldCell = fieldCell;
		this.node = node;		
	}
	
	public FieldCell getFieldCell() {
		return fieldCell;
	}
	public void setFieldCell(FieldCell fieldCell) {
		this.fieldCell = fieldCell;
	}
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}	
}
