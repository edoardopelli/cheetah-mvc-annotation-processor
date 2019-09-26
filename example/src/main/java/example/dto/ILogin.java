package example.dto;

import org.cheetah.spring.annotations.CheetahMapping;
import org.cheetah.spring.annotations.CheetahSpring;
@CheetahSpring(httprest="/login",pkgroot="example", entity="example.domain.Login", mappings= {
		@CheetahMapping(source="idUtente.idUtente", target="idUtente")
})
public interface ILogin {
LoginDto dto=new LoginDto();
}
