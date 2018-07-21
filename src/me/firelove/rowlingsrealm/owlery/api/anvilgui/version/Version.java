package me.firelove.rowlingsrealm.owlery.api.anvilgui.version;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import me.firelove.rowlingsrealm.owlery.api.anvilgui.version.impl.Wrapper1_12_R1;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Contains all of the {@link VersionWrapper}s
 * @author Wesley Smith
 * @since 1.0
 */
public enum Version {
    ONE_TWELVE_R1("1_12_R1", Wrapper1_12_R1.class);

    /**
     * A {@link LoadingCache} of VersionWrappers that are kept until 5 minutes of no use
     */
    private static final LoadingCache<Class<? extends VersionWrapper>, VersionWrapper> WRAPPER_CACHE =
            CacheBuilder.newBuilder()
                    .maximumSize(values().length)
                    .expireAfterWrite(5, TimeUnit.MINUTES)
                    .build(new CacheLoader<Class<? extends VersionWrapper>, VersionWrapper>() {
                        @Override
                        public VersionWrapper load(Class<? extends VersionWrapper> aClass) throws Exception {
                            return aClass.newInstance();
                        }
                    });

    /**
     * The package value of this NMS version
     */
    private final String pkg;
    /**
     * The {@link VersionWrapper} class for this NMS version
     */
    private final Class<? extends VersionWrapper> wrapper;

    /**
     * Creates a new value Version value
     * @param pkg The package value of this NMS version
     * @param wrapper The {@link VersionWrapper} class for this NMS version
     */
    Version(String pkg, Class<? extends VersionWrapper> wrapper) {
        this.pkg = pkg;
        this.wrapper = wrapper;
    }

    /**
     * Gets the package value of this NMS version
     * @return The package value
     */
    public String getPkg() {
        return pkg;
    }

    /**
     * Gets the {@link VersionWrapper} for this NMS version
     * @return The {@link VersionWrapper} for this NMS version
     */
    public VersionWrapper getWrapper() {
        try {
            return WRAPPER_CACHE.get(wrapper);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds the {@link Version} from the NMS package value
     * @param pkg The NMS package value
     * @return The {@link Version}, or null if no version is found
     */
    public static Version of(final String pkg) {
        return Arrays.stream(values()).filter(ver -> pkg.equals("v" + ver.getPkg())).findFirst().orElse(null);
    }

}
