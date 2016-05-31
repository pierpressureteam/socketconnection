package objectslibrary;

import java.io.Serializable;

/**
 *
 * @author Roy van den Heuvel
 */
public class SocketObjectWrapper implements Serializable
{
    private final SocketObject socketObject;
    private final int methodToCall;
  
    public SocketObjectWrapper(SocketObject so, int method)
    {
        this.socketObject = so;
        this.methodToCall = method;
    }

    public SocketObject getSocketObject()
    {
        return socketObject;
    }

    public int getMethodToCall()
    {
        return methodToCall;
    } 
}
