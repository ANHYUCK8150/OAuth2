package com.oauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.api.entity.User;
import com.exception.OAuth2AuthenticationProcessingException;
import com.oauth.entity.AuthProvider;
import com.oauth.entity.UserPrincipal;
import com.oauth.info.OAuth2UserInfo;
import com.oauth.info.OAuth2UserInfoFactory;
import com.oauth.repository.UserRepository;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    // 시용자 정보 추출
    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws UnsupportedEncodingException {
    	String ProviderId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(ProviderId, oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("OAuth2 공급자(구글, 네이버, ...) 에서 이메일을 찾을 수 없습니다.");
        }

        //Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        //이메일이 같은 경우 ProviderId와 같이 검증해야함.
        Optional<User> userOptional = userRepository.findByEmailAndProviderId(oAuth2UserInfo.getEmail(),ProviderId);
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException(
                        user.getProvider() + "계정을 사용하기 위해서 로그인을 해야합니다.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    // DB에 존재하지 않을 경우 새로 등록
    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {

        return userRepository.save(User.builder()
                .name(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .provider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .providerId(oAuth2UserInfo.getId())
                .build()
        );
    }

    // DB에 존재할 경우 정보 업데이트
    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {

        return userRepository.save(existingUser
                .update(
                        oAuth2UserInfo.getName(),
                        oAuth2UserInfo.getImageUrl()
                )
        );
    }
}
