package com.dodles.gdx.dodleengine.tools.importTool;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.dodles.gdx.dodleengine.PerDodleEngine;
import com.dodles.gdx.dodleengine.assets.AssetProvider;
import com.dodles.gdx.dodleengine.assets.StringAssets;
import com.dodles.gdx.dodleengine.editor.AbstractEditorView;
import com.dodles.gdx.dodleengine.editor.OkCancelStackManager;
import com.dodles.gdx.dodleengine.tools.ToolRegistry;
import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.vis.util.VisLml;

import javax.inject.Inject;

/**
 * Overlay for the import tool in the full editor.
 */
@PerDodleEngine
public class ImportToolFullEditorOverlay extends AbstractEditorView {
    private final AssetProvider assetProvider;
    private final ToolRegistry toolRegistry;
    private final OkCancelStackManager okCancelStack;
    
    private Table rootTable;
    
    @Inject
    public ImportToolFullEditorOverlay(AssetProvider assetProvider, ToolRegistry toolRegistry, OkCancelStackManager okCancelStack) {
        this.assetProvider = assetProvider;
        this.toolRegistry = toolRegistry;
        this.okCancelStack = okCancelStack;
    }
    
    @Override
    public final void activate(Skin skin, String newState) {        
        if (rootTable == null) {
            LmlParser parser = VisLml.parser()
                .skin(skin)
                .build();

            rootTable = (Table) parser.parseTemplate(assetProvider.getString(StringAssets.TEMPLATE_FULL_EDITOR_EMPTY_OVERLAY)).get(0);
            rootTable.setBackground(skin.getDrawable(toolRegistry.getActiveTool().getActivatedColor()));
            
            this.addActor(rootTable);
        }
        
        okCancelStack.push(new Runnable() {
            @Override
            public void run() {
                toolRegistry.setActiveTool(null);
            }
        });
    }

    @Override
    public final void deactivate() {
    }

    @Override
    protected final WidgetGroup getRootWidget() {
        return rootTable;
    }
}
