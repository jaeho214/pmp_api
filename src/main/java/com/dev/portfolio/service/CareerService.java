package com.dev.portfolio.service;
/*
경력사항을 처리하는 서비스
경력 삽입/ 경력 수정/ 경력 삭제/ 경력 불러와서 출력
 */

import com.dev.portfolio.model.dto.CareerDto;
import com.dev.portfolio.model.entity.Career;
import com.dev.portfolio.repository.CareerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Log
public class CareerService {

    private CareerRepository careerRepository;

//    public CareerService(CareerRepository careerRepository){
//        this.careerRepository = careerRepository;
//    }

    //모든 경력사항의 모든 내용을 가져오는 메소드
    public List<CareerDto> getCareers(){
        List<CareerDto> careerDtoList = new ArrayList<>();
        List<Career> careers = careerRepository.findAll();
        careers.forEach((career) -> {
            careerDtoList.add(
                    CareerDto.builder()
                        .company(career.getCompany())
                        .department(career.getDepartment())
                        .details(career.getDetails())
                        .reason(career.getReason())
                        .term(career.getTerm())
                        .build()
            );
        });
        return careerDtoList;
    }

    //하나의 경력을 추가하는 메소드
    public void saveCareer(CareerDto careerDto){
        careerRepository.save(careerDto.toEntity());
    }

    //경력을 수정하는 메소드
    public void updateCareer(CareerDto careerDto){
        log.info("---경력 수정---");
        Career career = careerRepository.findCareerEntityByCareerNo(careerDto.getCareerNo());
        career.updateCareer(careerDto);
        careerRepository.save(career);
    }

    public void deleteCareer(Long careerNo){
        Career career = careerRepository.findCareerEntityByCareerNo(careerNo);
        careerRepository.delete(career);
    }
}
