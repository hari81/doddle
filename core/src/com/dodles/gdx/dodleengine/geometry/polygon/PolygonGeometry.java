package com.dodles.gdx.dodleengine.geometry.polygon;

import com.dodles.gdx.dodleengine.PerDodleEngine;
import com.dodles.gdx.dodleengine.editor.full.FullEditorViewState;
import com.dodles.gdx.dodleengine.geometry.AbstractPolygonGeometry;
import com.dodles.gdx.dodleengine.geometry.GeometryConfig;
import com.dodles.gdx.dodleengine.geometry.GeometryRegistry;
import com.dodles.gdx.dodleengine.geometry.HandleHook;
import com.dodles.gdx.dodleengine.scenegraph.Shape;
import com.dodles.gdx.dodleengine.tools.geometry.GeometryTool;

import javax.inject.Inject;
import java.util.List;

/**
 * Draws polygonal geometric shapes.
 */
@PerDodleEngine
public class PolygonGeometry extends AbstractPolygonGeometry {
    public static final String GEOMETRY_NAME = "polygon";
    public static final String GEOMETRY_ICON_NAME = "Polygon";
    
    private GeometryConfig defaultGeometryConfig;
    
    @Inject
    public PolygonGeometry(GeometryRegistry registry, FullEditorViewState fullViewState, PolygonConfigurationRowEditorView rcrev) {
        registry.registerGeometry(this);
        
        String state = GeometryTool.TOOL_NAME + "." + GEOMETRY_NAME;
        fullViewState.registerRow2View(state, null);
        fullViewState.registerRow3View(state, rcrev);
    }

    @Override
    public final String getName() {
        return GEOMETRY_NAME;
    }

    @Override
    public final String getIconName() {
        return GEOMETRY_ICON_NAME;
    }

    @Override
    public final int getOrder() {
        return 1;
    }
    
    @Override
    public final GeometryConfig getDefaultGeometryConfig() {
        if (defaultGeometryConfig == null) {
            defaultGeometryConfig = new PolygonGeometryConfig();
        }
        
        return defaultGeometryConfig.cpy();
    }

    @Override
    public final void generateHandleHooks(Shape shape, List<HandleHook> handleHooks) {
        handleHooks.clear();
        
        final int initNumPoints = ((PolygonGeometryConfig) shape.getCustomConfig()).getNumPoints();
        for (int i = 0; i < initNumPoints; i++) {
            handleHooks.add(new PolygonHandleHook(shape, i));
        }
    }
}
