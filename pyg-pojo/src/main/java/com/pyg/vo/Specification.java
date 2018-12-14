package com.pyg.vo;

import com.pyg.pojo.TbSpecification;
import com.pyg.pojo.TbSpecificationOption;

import java.io.Serializable;
import java.util.List;

public class Specification implements Serializable {
    private TbSpecification tbSpecification;
    private List<TbSpecificationOption> tbSpecificationOptions;

    public Specification() {
    }

    public Specification(TbSpecification tbSpecification, List<TbSpecificationOption> tbSpecificationOptions) {
        this.tbSpecification = tbSpecification;
        this.tbSpecificationOptions = tbSpecificationOptions;
    }

    public TbSpecification getTbSpecification() {
        return tbSpecification;
    }

    public void setTbSpecification(TbSpecification tbSpecification) {
        this.tbSpecification = tbSpecification;
    }

    public List<TbSpecificationOption> getTbSpecificationOptions() {
        return tbSpecificationOptions;
    }

    public void setTbSpecificationOptions(List<TbSpecificationOption> tbSpecificationOptions) {
        this.tbSpecificationOptions = tbSpecificationOptions;
    }
}
