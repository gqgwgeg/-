package pyg.shang.Service;

import com.pyg.pojo.TbGoods;
import com.pyg.vo.Goods;
import pyg.com.utis.PageResult;

import java.util.List;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface GoodsService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbGoods> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(Goods goodsDesc);
	
	
	/**
	 * 修改
	 */
	public void update(TbGoods goodsDesc);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbGoods findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(TbGoods goodsDesc, int pageNum, int pageSize);

	/**
	 * 修改状态审核
	 */

	 public void updateStatus(String status,Long[] ids);

	/**
	 * 修改上下架状态
	 */
	void isMarketable(String isMarketable,Long[] ids);
}
