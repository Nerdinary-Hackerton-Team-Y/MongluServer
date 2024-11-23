package com.mongle.api.service;

import org.springframework.stereotype.Service;

@Service
public class RandomNicknameServiceImpl implements RandomNicknameService {

    private String[] firstList = {"기철초풍", "멋있는", "재미있는"};
    private String[] secondList = {"도전적인", "노란색의", "바보같은"};
    private String[] thirdList = {"돌고래", "개발자", "오랑우탄"};

    @Override
    public String getRandomNickname(int seed) {
        int totalCount = firstList.length + secondList.length + thirdList.length;

        int index = seed % totalCount;

        String first = firstList[index / (firstList.length + secondList.length)];
        String second = secondList[(index / secondList.length) % firstList.length];
        String third = thirdList[index % secondList.length];

        return first + " " + second + " " + third;
    }

}
