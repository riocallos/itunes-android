package com.riocallos.itunes.databinding

/**
 * Base class that extends DataBindingComponent to handle
 * data binding initialization.
 *
 */
class BaseDataBindingComponent : androidx.databinding.DataBindingComponent {

    override fun getImageViewDataBinding(): ImageViewDataBinding {
        return ImageViewDataBinding()
    }

    override fun getRecyclerViewDataBinding(): RecyclerViewDataBinding {
        return RecyclerViewDataBinding()
    }

}
