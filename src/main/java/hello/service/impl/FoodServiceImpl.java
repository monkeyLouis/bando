package hello.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.domain.Food;
import hello.repository.FoodRepository;
import hello.service.FoodService;

@Service("foodSrvc")
public class FoodServiceImpl implements FoodService {

	@Autowired
	private FoodRepository foodRepository;
	
	@Override
	public Food save(Food food) {
		return foodRepository.save(food);
	}

}
