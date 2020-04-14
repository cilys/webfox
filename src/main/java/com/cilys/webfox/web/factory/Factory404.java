package com.cilys.webfox.web.factory;

import com.jfinal.render.Render;
import com.jfinal.render.RenderFactory;

/**
 * Created by 123 on 2018/5/4.
 */
public class Factory404 extends RenderFactory {

    @Override
    public Render getErrorRender(int errorCode) {
        if (errorCode == 404){
//            return new JsonRender(ResUtils.res())
        }
        return super.getErrorRender(errorCode);
    }
}
