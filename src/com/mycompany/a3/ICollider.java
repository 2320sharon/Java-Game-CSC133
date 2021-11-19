package com.mycompany.a3;

public interface ICollider {
	boolean collidesWith(ICollider collideObject);
	void handleCollision(ICollider collideObject);
}
